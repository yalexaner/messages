package yalexaner.messages.models

import android.content.Context
import android.provider.Telephony.Sms
import android.provider.Telephony.TextBasedSmsColumns.*
import android.provider.Telephony.Threads
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import yalexaner.messages.data.conversations.Conversation
import yalexaner.messages.data.conversations.ConversationsEvent
import yalexaner.messages.data.conversations.ConversationsState
import yalexaner.messages.other.getAsInt
import yalexaner.messages.other.getAsLong
import yalexaner.messages.other.getAsString
import yalexaner.messages.other.getCursor
import java.util.*

class ConversationsViewModel(private val context: Context) : ViewModel() {
    val state: LiveData<ConversationsState> = liveData {
        emit(ConversationsState.Loading)

        val conversations = getConversations()
        if (conversations.isEmpty()) {
            emit(ConversationsState.LoadedNothing)
        } else {
            emit(ConversationsState.Loaded(conversations = conversations))
        }
    }

    fun obtain(intent: ConversationsEvent) {
        when (intent) {
            is ConversationsEvent.OpenMessagesScreen -> TODO()
        }
    }

    private suspend fun getConversations() = withContext(Dispatchers.IO) {
        val cursor = context.getCursor(
            contentUri = Threads.CONTENT_URI,
            projection = arrayOf(THREAD_ID, ADDRESS, BODY, DATE),
            sortOrder = Sms.DEFAULT_SORT_ORDER
        )
        val conversations: MutableList<Conversation> = mutableListOf()

        while (cursor?.moveToNext() == true && isActive) {
            val threadId = cursor.getAsInt(THREAD_ID)
            val address = cursor.getAsString(ADDRESS)
            val body = cursor.getAsString(BODY)
            val milliseconds = cursor.getAsLong(DATE)

            conversations.add(Conversation(threadId, address, body, Date(milliseconds)))
        }

        cursor?.close()

        conversations
    }
}

@Suppress("UNCHECKED_CAST")
class ConversationsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversationsViewModel::class.java)) {
            return ConversationsViewModel(context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}