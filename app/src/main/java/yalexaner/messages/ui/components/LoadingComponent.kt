package yalexaner.messages.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(modifier = modifier) {
        Text(modifier = modifier.align(alignment = Alignment.Center), text = message)
    }
}