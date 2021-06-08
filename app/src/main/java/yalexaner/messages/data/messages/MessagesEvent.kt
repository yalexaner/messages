package yalexaner.messages.data.messages

import androidx.compose.foundation.lazy.LazyListState

sealed class MessagesEvent {

    data class LoadMessages(val threadId: String) : MessagesEvent()

    data class ShowOptionsMenu(
        val message: Message,
        val savedListPosition: LazyListState
    ) : MessagesEvent()

    object CloseOptionsMenu : MessagesEvent()
}