package yalexaner.messages.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle

@Composable
fun ClickableTextComponent(
    text: String,
    style: TextStyle,
    color: Color,
    onClick: ((offset: Int) -> Unit)? = null,
    onLongClick: ((offset: Int) -> Unit)? = null,
    onDoubleClick: ((offset: Int) -> Unit)? = null
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val gesture = Modifier.pointerInput(null) {
        // excluding onDoubleTap from the one with onTap
        // so the response would be faster in MessageComponent
        if (onClick != null) {
            detectTapGestures(
                onTap = { position ->
                    layoutResult.value?.let { layout ->
                        onClick.invoke(layout.getOffsetForPosition(position))
                    }
                },
                onLongPress = { position ->
                    layoutResult.value?.let { layout ->
                        onLongClick?.invoke(layout.getOffsetForPosition(position))
                    }
                }
            )
        }

        // no need for onClick here:
        // basically would used with OptionsMenuComponent
        if (onDoubleClick != null) {
            detectTapGestures(
                onLongPress = { position ->
                    layoutResult.value?.let { layout ->
                        onLongClick?.invoke(layout.getOffsetForPosition(position))
                    }
                },
                onDoubleTap = { position ->
                    layoutResult.value?.let { layout ->
                        onDoubleClick(layout.getOffsetForPosition(position))
                    }
                }
            )
        }
    }

    Text(
        modifier = Modifier.then(gesture),
        text = text,
        style = style,
        color = color,
        onTextLayout = { layoutResult.value = it }
    )
}