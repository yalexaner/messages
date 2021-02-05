package yalexaner.messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.setContent
import yalexaner.messages.other.AmbientAppCompatActivity
import yalexaner.messages.ui.theme.MessagesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessagesTheme {
                Providers(AmbientAppCompatActivity provides this) {
                    Navigation()
                }
            }
        }
    }
}