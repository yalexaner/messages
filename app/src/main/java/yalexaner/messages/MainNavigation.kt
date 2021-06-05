package yalexaner.messages

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yalexaner.messages.data.messages.MessagesEvent
import yalexaner.messages.data.messages.MessagesViewModel
import yalexaner.messages.ui.screens.ConversationsScreen
import yalexaner.messages.ui.screens.MessagesScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Conversations.generic) {
        composable(Route.Conversations.generic) {
            ConversationsScreen { navController.navigate(Route.Messages.with(argument = it)) }
        }

        composable(Route.Messages.generic) { backStackEntry ->
            val threadId = backStackEntry.arguments?.getString(Route.Messages.THREAD_ID)!!
            val model: MessagesViewModel = hiltViewModel()
            model.obtain(intent = MessagesEvent.LoadMessages(threadId))

            MessagesScreen(model = model)
        }
    }
}

object Route {

    object Conversations {

        private const val NAME = "conversation"

        const val generic = NAME
    }

    object Messages {

        private const val NAME = "messages"

        const val THREAD_ID = "threadId"

        const val generic = "$NAME/{$THREAD_ID}"
        fun with(argument: String) = "$NAME/$argument"
    }
}