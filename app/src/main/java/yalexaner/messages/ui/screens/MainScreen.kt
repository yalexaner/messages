package yalexaner.messages.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import yalexaner.messages.Route

@Composable
fun MainScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        TextButton(
            onClick = { navController.navigate(Route.MESSAGES_SCREEN) },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Show messages")
        }
    }
}