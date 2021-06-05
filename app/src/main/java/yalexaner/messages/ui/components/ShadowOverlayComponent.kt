package yalexaner.messages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import yalexaner.messages.other.noRippleClickable

@Composable
fun ShadowOverlayComponent(onClick: () -> Unit) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC000000))
            .noRippleClickable { onClick() },
    )
}