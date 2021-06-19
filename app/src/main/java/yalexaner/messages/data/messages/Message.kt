package yalexaner.messages.data.messages

data class Message(
    val address: String,
    val body: String,
    val date: Long,
    val type: Type
) {

    enum class Type {
        INBOX, OUTBOX, READONLY
    }
}