package yalexaner.messages.data.options

import androidx.compose.ui.graphics.vector.ImageVector

data class Option(
    val icon: ImageVector,
    val text: String,
    val action: () -> Unit
)