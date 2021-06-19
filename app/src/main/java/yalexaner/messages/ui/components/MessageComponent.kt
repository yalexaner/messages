package yalexaner.messages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import yalexaner.messages.data.messages.Message
import yalexaner.messages.other.noRippleCombinedClickable
import yalexaner.messages.other.toFormattedString
import java.util.*

@Composable
fun MessageComponent(
    modifier: Modifier = Modifier,
    message: Message,
    textColor: Color,
    surfaceColor: Color,
    surfaceShape: Shape,
    alignment: Alignment,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .noRippleCombinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Column(
            modifier = Modifier
                .align(alignment = alignment)
                .background(color = surfaceColor, shape = surfaceShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = message.body,
                style = MaterialTheme.typography.subtitle1,
                color = textColor
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = Date(message.date).toFormattedString("h:mm a"),
                style = MaterialTheme.typography.overline,
                color = textColor
            )
        }
    }
}