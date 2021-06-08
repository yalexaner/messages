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
import yalexaner.messages.data.options.OptionsHandler
import yalexaner.messages.other.getAsInt
import yalexaner.messages.other.getAsLong
import yalexaner.messages.other.getAsString
import yalexaner.messages.other.getCursor
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MessagesViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var messages: List<Message> = emptyList()
    private var listPosition: LazyListState? = null

    private val _state = MutableLiveData<MessagesState>(MessagesState.Loading)
    val state: LiveData<MessagesState> = _state

    private val _optionsMenuState = MutableLiveData<OptionsMenuState>(OptionsMenuState.Hiding)
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
            projection = arrayOf(THREAD_ID, BODY, DATE, TYPE),
            selection = "$THREAD_ID = ?",
            selectionArguments = arrayOf(threadId),
            sortOrder = "date ASC"
        ) ?: return@withContext emptyList()

        val conversations: MutableList<Message> = mutableListOf()

        while (cursor.moveToNext() && isActive) {
            val body = cursor.getAsString(BODY)
            val date = cursor.getAsLong(DATE)
            val type = cursor.getAsInt(TYPE)

            conversations.add(
                Message(
                    body,
                    date,
                    if (type == MESSAGE_TYPE_INBOX) Message.Type.INBOX else Message.Type.OUTBOX
                )
            )
        }

        cursor.close()

        conversations
    }

    private fun showOptionsMenu(intent: MessagesEvent.ShowOptionsMenu) {
        listPosition = intent.savedListPosition
        _optionsMenuState.value = OptionsMenuState.Showing(
            message = intent.message,
            options = OptionsHandler.get,
            onClose = { obtain(intent = MessagesEvent.CloseOptionsMenu) }
        )
    }

    private fun closeOptionsMenu() {
        _optionsMenuState.value = OptionsMenuState.Hiding
    }
}