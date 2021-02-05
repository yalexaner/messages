package yalexaner.messages.data

import android.content.Context
import android.database.Cursor
import android.net.Uri
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
import yalexaner.messages.other.getAsInt
import yalexaner.messages.other.getAsLong
import yalexaner.messages.other.getAsString

class MainViewModel(context: Context) : ViewModel() {
    val conversations: LiveData<List<Conversation>> = liveData {
        emit(getConversations(context))
    }
}

private suspend fun getConversations(context: Context) = withContext(Dispatchers.IO) {
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
        val date = cursor.getAsLong(DATE)

        conversations.add(Conversation(threadId, address, body, date))
    }

    cursor?.close()

    conversations
}

private fun Context.getCursor(
    contentUri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArguments: Array<String>? = null,
    sortOrder: String? = null
): Cursor? = contentResolver.query(
    contentUri,
    projection,
    selection,
    selectionArguments,
    sortOrder
)

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}