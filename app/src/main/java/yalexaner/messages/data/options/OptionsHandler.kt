package yalexaner.messages.data.options

import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.TextFields

class OptionsHandler(
    copyAction: () -> Unit = {},
    selectAction: () -> Unit = {},
    deleteAction: () -> Unit = {},
    cancelAction: () -> Unit = {}
) {

    val get: List<Option> = listOf(
        Option(icon = Outlined.ContentCopy, text = "Copy message", action = copyAction),
        Option(icon = Rounded.TextFields, text = "Select text", action = selectAction),
        Option(icon = Outlined.Delete, text = "Delete message", action = deleteAction),
        Option(icon = Rounded.Close, text = "Cancel", action = cancelAction)
    )
}