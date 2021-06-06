package yalexaner.messages.data.options

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.TextFields

object OptionsHandler {

    val get: List<Option> = listOf(
        Option(icon = Icons.Outlined.ContentCopy, text = "Copy message", type = Option.Type.COPY),
        Option(icon = Icons.Rounded.TextFields, text = "Select text", type = Option.Type.SELECT),
        Option(icon = Icons.Outlined.Delete, text = "Delete message", type = Option.Type.DELETE),
        Option(icon = Icons.Rounded.Close, text = "Cancel", type = Option.Type.CANCEL)
    )
}