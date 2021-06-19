package yalexaner.messages.data.conversations

sealed class ConversationsState {

    object Loading : ConversationsState()

    object ShowingNothing : ConversationsState()

    data class ShowingConversations(
        val conversations: List<Conversation>
    ) : ConversationsState()
}