package yalexaner.messages.other

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.ambientOf
import yalexaner.messages.permission.PermissionHandler

val AmbientPermissionHandler = ambientOf<PermissionHandler>()
val AmbientAppCompatActivity = ambientOf<AppCompatActivity>()