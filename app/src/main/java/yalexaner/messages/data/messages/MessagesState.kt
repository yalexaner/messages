package yalexaner.messages.data.messages

sealed class MessagesState {

    object Loading : MessagesState()

    object LoadedNothing : MessagesState()

    class Loaded(val messages: List<Message>) : MessagesState()
}