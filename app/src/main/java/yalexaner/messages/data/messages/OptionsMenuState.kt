package yalexaner.messages.data.messages

import yalexaner.messages.data.options.Option

sealed class OptionsMenuState {

    class Showing(
        val message: Message,
        val options: List<Option>,
        val onClose: () -> Unit
    ) : OptionsMenuState()

    object Hiding : OptionsMenuState()
}
