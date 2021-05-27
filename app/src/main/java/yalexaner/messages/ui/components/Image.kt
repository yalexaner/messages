package yalexaner.messages.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import yalexaner.messages.ui.theme.indianRed

@Composable
fun Image(image: ImageVector) {
    androidx.compose.foundation.Image(
        imageVector = image,
        colorFilter = ColorFilter.tint(color = indianRed),
        contentDescription = null,
        modifier = Modifier
            .size(64.dp)
            .padding(end = 8.dp)
    )
}