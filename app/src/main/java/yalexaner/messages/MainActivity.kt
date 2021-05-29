package yalexaner.messages

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import dagger.hilt.android.AndroidEntryPoint
import yalexaner.messages.models.ConversationsViewModel
import yalexaner.messages.permission.PermissionHandler
import yalexaner.messages.ui.theme.MessagesTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var model: ConversationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessagesTheme {
                CompositionLocalProvider(LocalAppCompatActivity provides this) {
                    Navigation(model = model)
                }
            }
        }
    }

    companion object {
        val LocalAppCompatActivity = compositionLocalOf { AppCompatActivity() }
        val LocalPermissionHandler = compositionLocalOf { PermissionHandler(AppCompatActivity()) }
    }
}