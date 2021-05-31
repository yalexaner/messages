package yalexaner.messages.other

import android.content.Context
import android.database.Cursor
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

fun Cursor.getAsLong(name: String): Long = getLong(getColumnIndex(name))
fun Cursor.getAsString(name: String): String = getString(getColumnIndex(name))

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