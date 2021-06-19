package yalexaner.messages.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200
)

private val LightColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun MessagesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

object MessagesTextStyle {

    val conversationsAddress = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
    val conversationsDate = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
    val conversationsSnippet = TextStyle( fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray )
}

object MessagesCornersShape {

    val INBOX = RoundedCornerShape(
        topStart = 15.dp,
        topEnd = 15.dp,
        bottomEnd = 15.dp
    )

    val OUTBOX = RoundedCornerShape(
        topStart = 15.dp,
        topEnd = 15.dp,
        bottomStart = 15.dp
    )
}