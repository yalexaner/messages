package yalexaner.messages.data.conversations

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Telephony.Sms
import android.provider.Telephony.TextBasedSmsColumns.*
import android.provider.Telephony.Threads
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import yalexaner.messages.other.getAsLong
import yalexaner.messages.other.getAsString
import yalexaner.messages.other.getCursor
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class ConversationsViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    val state: LiveData<ConversationsState> = liveData {
        emit(ConversationsState.Loading)

        val conversations = getConversations()
        if (conversations.isEmpty()) {
            emit(ConversationsState.ShowingNothing)
        } else {
            emit(ConversationsState.ShowingConversations(conversations = conversations))
        }
    }

    private suspend fun getConversations(): List<Conversation> = withContext(Dispatchers.IO) {
        val cursor = context.getCursor(
            contentUri = Threads.CONTENT_URI,
            projection = arrayOf(THREAD_ID, ADDRESS, BODY, DATE),
            sortOrder = Sms.DEFAULT_SORT_ORDER
        ) ?: return@withContext emptyList()

        val conversations: MutableList<Conversation> = mutableListOf()

        while (cursor.moveToNext() && isActive) {
            val threadId = cursor.getAsString(THREAD_ID)
            val address = cursor.getAsString(ADDRESS)
            val body = cursor.getAsString(BODY)
            val milliseconds = cursor.getAsLong(DATE)

            conversations.add(Conversation(threadId, address, body, Date(milliseconds)))
        }

        cursor.close()

        conversations
    }
}