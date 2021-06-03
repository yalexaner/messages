package yalexaner.messages.data.messages

import yalexaner.messages.data.Option

sealed class MessagesState {

    object Loading : MessagesState()

    object LoadedNothing : MessagesState()

    class Loaded(val messages: List<Message>) : MessagesState()

    class ShowOptionsMenu(
        val messages: List<Message>,
        val message: Message,
        val options: List<Option>
    ) : MessagesState()
}