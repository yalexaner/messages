package yalexaner.messages.data.messages

import androidx.compose.foundation.lazy.LazyListState
import yalexaner.messages.data.options.Option

sealed class MessagesState {

    object Loading : MessagesState()

    object ShowingNothing : MessagesState()

    data class ShowingMessages(
        val messages: List<Message>,
        val savedListPosition: LazyListState?
    ) : MessagesState()

    data class ShowingOptionsMenu(
        val message: Message,
        val options: List<Option>,
        val messagesState: ShowingMessages
    ) : MessagesState()
}