package yalexaner.messages.data.enteties

import java.util.*

data class Conversation(
    val threadId: Int,
    val address: String,
    val body: String,
    val date: Date
)