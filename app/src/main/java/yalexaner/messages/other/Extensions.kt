package yalexaner.messages.other

import android.database.Cursor

fun Cursor.getAsInt(name: String): Int = getInt(getColumnIndex(name))
fun Cursor.getAsLong(name: String): Long = getLong(getColumnIndex(name))
fun Cursor.getAsString(name: String): String = getString(getColumnIndex(name))