package yalexaner.messages.data.conversations

import java.util.*

data class Conversation(
    val threadId: String,
    val address: String,
    val body: String,
    val date: Date
)