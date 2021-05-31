package yalexaner.messages.data.messages

sealed class MessagesEvent {

    class LoadMessages(val threadId: String) : MessagesEvent()
}