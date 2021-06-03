package yalexaner.messages.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.TextFields
import androidx.compose.ui.graphics.vector.ImageVector

data class Option(val icon: ImageVector, val text: String)

object OptionsHandler {

    val get: List<Option> = listOf(
        Option(icon = Icons.Outlined.ContentCopy, text = "Copy message"),
        Option(icon = Icons.Rounded.TextFields, text = "Select text"),
        Option(icon = Icons.Outlined.Delete, text = "Delete message"),
        Option(icon = Icons.Rounded.Close, text = "Cancel")
    )
}