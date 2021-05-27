package yalexaner.messages.data.conversations

sealed class ConversationsEvent {

    class OpenMessagesScreen(val messageId: Int): ConversationsEvent()
}