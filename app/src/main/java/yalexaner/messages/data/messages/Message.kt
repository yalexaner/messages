package yalexaner.messages.data.messages

data class Message(val body: String, val date: Long, val type: MessageType)

enum class MessageType {
    INBOX, OUTBOX
}