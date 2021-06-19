package yalexaner.messages.data.options

import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close

class OptionsHandler(
    copyAction: () -> Unit = {},
    deleteAction: () -> Unit = {},
    cancelAction: () -> Unit = {}
) {

    val get: List<Option> = listOf(
        Option(icon = Outlined.ContentCopy, text = "Copy message text", action = copyAction),
        Option(icon = Outlined.Delete, text = "Delete message", action = deleteAction),
        Option(icon = Rounded.Close, text = "Cancel menu", action = cancelAction)
    )
}