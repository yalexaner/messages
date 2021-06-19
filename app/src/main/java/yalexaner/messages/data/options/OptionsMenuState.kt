package yalexaner.messages.data.options

import yalexaner.messages.data.messages.Message

data class OptionsMenuState(
    val showing: Boolean = false,
    val message: Message? = null,
    val options: List<Option>? = null
)