package yalexaner.messages.data.messages

import yalexaner.messages.data.options.Option

data class OptionsMenuState(
    val showing: Boolean = false,
    val message: Message? = null,
    val options: List<Option>? = null
)