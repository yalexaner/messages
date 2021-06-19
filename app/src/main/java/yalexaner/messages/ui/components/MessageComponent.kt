package yalexaner.messages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import yalexaner.messages.data.messages.Message
import yalexaner.messages.other.toFormattedString
import java.util.*

@Composable
fun MessageComponent(
    modifier: Modifier = Modifier,
    message: Message,
    textColor: Color,
    surfaceColor: Color,
    surfaceShape: RoundedCornerShape,
    alignment: Alignment,
    onClick: (offset: Int) -> Unit = {},
    onLongClick: ((offset: Int) -> Unit)? = null,
    onDoubleClick: ((offset: Int) -> Unit)? = null
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(alignment = alignment)
                .background(color = surfaceColor, shape = surfaceShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            ClickableTextComponent(
                text = message.body,
                style = MaterialTheme.typography.subtitle1,
                color = textColor,
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick
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