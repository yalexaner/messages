package yalexaner.messages.data.conversations

sealed class ConversationsState {

    object Loading: ConversationsState()

    object LoadedNothing: ConversationsState()

    class Loaded(val conversations: List<Conversation>): ConversationsState()
}