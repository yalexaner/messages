package yalexaner.messages.other

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

fun Cursor.getAsLong(name: String): Long = getLong(getColumnIndex(name))
fun Cursor.getAsString(name: String): String = getString(getColumnIndex(name))
fun Cursor.getAsInt(name: String): Int = getInt(getColumnIndex(name))

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.noRippleCombinedClickable(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null
): Modifier = composed {
    combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick
    )
}

fun Date.toFormattedString(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun Context.getCursor(
    contentUri: Uri,
    projection: Array<String>? = null,
    selection: String? = null,
    selectionArguments: Array<String>? = null,
    sortOrder: String? = null
): Cursor? = contentResolver.query(
    contentUri,
    projection,
    selection,
    selectionArguments,
    sortOrder
)

fun Context.copyToClipboard(text: String) {
    val clipboardManager = ContextCompat.getSystemService(this, ClipboardManager::class.java)!!
    val clip = ClipData.newPlainText("clipboard", text)
    clipboardManager.setPrimaryClip(clip)
}