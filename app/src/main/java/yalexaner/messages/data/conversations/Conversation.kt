package yalexaner.messages.data.conversations

import java.util.*

data class Conversation(
    val threadId: String,
    val address: String,
    val snippet: String,
    val date: Date
)