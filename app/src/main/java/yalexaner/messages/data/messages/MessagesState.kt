package yalexaner.messages.data.messages

import androidx.compose.foundation.lazy.LazyListState

sealed class MessagesState {

    object Loading : MessagesState()

    object ShowingNothing : MessagesState()

    data class ShowingMessages(
        val messages: List<Message>,
        val savedListPosition: LazyListState?
    ) : MessagesState()
}