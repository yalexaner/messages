package yalexaner.messages

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import yalexaner.messages.permission.PermissionHandler
import yalexaner.messages.ui.theme.MessagesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessagesTheme {
                CompositionLocalProvider(LocalAppCompatActivity provides this) {
                    Navigation()
                }
            }
        }
    }

    companion object {
        val LocalAppCompatActivity = compositionLocalOf { AppCompatActivity() }
        val LocalPermissionHandler = compositionLocalOf { PermissionHandler(AppCompatActivity()) }
    }
}