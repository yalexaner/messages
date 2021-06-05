package yalexaner.messages.data.options

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.TextFields

object OptionsHandler {

    val get: List<Option> = listOf(
        Option(icon = Icons.Outlined.ContentCopy, text = "Copy message"),
        Option(icon = Icons.Rounded.TextFields, text = "Select text"),
        Option(icon = Icons.Outlined.Delete, text = "Delete message"),
        Option(icon = Icons.Rounded.Close, text = "Cancel")
    )
}