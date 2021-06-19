package yalexaner.messages.data.messages

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Telephony.Sms.CONTENT_URI
import android.provider.Telephony.TextBasedSmsColumns.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yalexaner.messages.data.floatingnotification.FloatingNotificationState
import yalexaner.messages.data.options.OptionsHandler
import yalexaner.messages.data.options.OptionsMenuState
import yalexaner.messages.other.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MessagesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val floatingNotificationState: FloatingNotificationState
) : ViewModel() {

    private var messages: List<Message> = emptyList()
    private var listPosition: LazyListState? = null

    private val _state = MutableLiveData<MessagesState>(MessagesState.Loading)
    val state: LiveData<MessagesState> = _state

    private val _optionsMenuState = MutableLiveData(OptionsMenuState())
    val optionsMenuState: LiveData<OptionsMenuState> = _optionsMenuState

    fun obtain(intent: MessagesEvent) {
        when (intent) {
            is MessagesEvent.LoadMessages -> viewModelScope.launch { loadMessages(intent.threadId) }
            is MessagesEvent.ShowOptionsMenu -> showOptionsMenu(intent)
            is MessagesEvent.CloseOptionsMenu -> closeOptionsMenu()
        }
    }

    private suspend fun loadMessages(threadId: String) {
        messages = getMessages(threadId)

        _state.value = if (messages.isEmpty()) {
            MessagesState.ShowingNothing
        } else {
            MessagesState.ShowingMessages(messages = messages, savedListPosition = listPosition)
        }
    }

    private suspend fun getMessages(threadId: String) = withContext(Dispatchers.IO) {
        val cursor = context.getCursor(
            contentUri = CONTENT_URI,
            projection = arrayOf(THREAD_ID, ADDRESS, BODY, DATE, TYPE),
            selection = "$THREAD_ID = ?",
            selectionArguments = arrayOf(threadId),
            sortOrder = "date ASC"
        ) ?: return@withContext emptyList()

        val conversations: MutableList<Message> = mutableListOf()

        while (cursor.moveToNext() && isActive) {
            val address = cursor.getAsString(ADDRESS)
            val body = cursor.getAsString(BODY)
            val date = cursor.getAsLong(DATE)
            val type = when {
                address.notPhoneNumber -> Message.Type.READONLY
                cursor.getAsInt(TYPE) == MESSAGE_TYPE_INBOX -> Message.Type.INBOX
                else -> Message.Type.OUTBOX
            }

            conversations.add(Message(address, body, date, type))
        }

        cursor.close()

        conversations
    }

    private fun showOptionsMenu(intent: MessagesEvent.ShowOptionsMenu) {
        listPosition = intent.savedListPosition

        val options = OptionsHandler(
            copyAction = {
                context.copyToClipboard(intent.message.body)
                floatingNotificationState.showNotification("Message text copied")
            },
            cancelAction = { obtain(intent = MessagesEvent.CloseOptionsMenu) }
        ).get

        _optionsMenuState.value = OptionsMenuState(
            showing = true,
            message = intent.message,
            options = options
        )
    }

    private fun closeOptionsMenu() {
        _optionsMenuState.value = _optionsMenuState.value?.copy(showing = false)
    }
}