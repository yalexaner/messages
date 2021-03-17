package yalexaner.messages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yalexaner.messages.ui.screens.ConversationsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MESSAGES_SCREEN) {
        composable(Route.MESSAGES_SCREEN) { ConversationsScreen() }
    }
}

object Route {
    const val MESSAGES_SCREEN = "messages_screen"
}