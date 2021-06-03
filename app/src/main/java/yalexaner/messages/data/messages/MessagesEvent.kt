package yalexaner.messages.data.messages

sealed class MessagesEvent {

    class LoadMessages(val threadId: String) : MessagesEvent()

    class ShowOptionsMenu(val message: Message) : MessagesEvent()

    object CloseOptionsMenu : MessagesEvent()
}