package yalexaner.messages.models

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Telephony.Sms.CONTENT_URI
import android.provider.Telephony.Sms.DEFAULT_SORT_ORDER
import android.provider.Telephony.TextBasedSmsColumns.*
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessagesEvent
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.other.getAsString
import yalexaner.messages.other.getCursor
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MessagesViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableLiveData<MessagesState>(MessagesState.Loading)
    val state: LiveData<MessagesState>
        get() = _state

    fun obtain(intent: MessagesEvent) {
        when (intent) {
            is MessagesEvent.LoadMessages -> viewModelScope.launch { loadMessages(intent.threadId) }
        }
    }

    private suspend fun loadMessages(threadId: String) {
        val messages = getMessages(threadId)

        _state.value = if (messages.isEmpty()) {
            MessagesState.LoadedNothing
        } else {
            MessagesState.Loaded(messages = messages)
        }
    }

    private suspend fun getMessages(threadId: String) = withContext(Dispatchers.IO) {
        val cursor = context.getCursor(
            contentUri = CONTENT_URI,
            projection = arrayOf(THREAD_ID, BODY, DATE),
            selection = "$THREAD_ID = ?",
            selectionArguments = arrayOf(threadId),
            sortOrder = DEFAULT_SORT_ORDER
        ) ?: return@withContext emptyList()

        val conversations: MutableList<Message> = mutableListOf()

        while (cursor.moveToNext() && isActive) {
            val body = cursor.getAsString(BODY)

            conversations.add(Message(body))
        }

        cursor.close()

        conversations
    }
}