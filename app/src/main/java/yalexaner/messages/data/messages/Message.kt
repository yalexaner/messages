package yalexaner.messages.data.messages

data class Message(val body: String, val date: Long, val type: Type) {

    enum class Type {
        INBOX, OUTBOX
    }
}