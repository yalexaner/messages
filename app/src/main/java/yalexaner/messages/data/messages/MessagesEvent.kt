package yalexaner.messages.data.messages

import androidx.compose.foundation.lazy.LazyListState

sealed class MessagesEvent {

    class LoadMessages(val threadId: String) : MessagesEvent()

    class ShowOptionsMenu(val message: Message, val saveListState: LazyListState) : MessagesEvent()

    object CloseOptionsMenu : MessagesEvent()
}