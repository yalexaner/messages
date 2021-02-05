package yalexaner.messages.data

data class Conversation(
    val threadId: Int,
    val address: String,
    val body: String,
    val date: Long
)