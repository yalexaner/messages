package yalexaner.messages.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import yalexaner.messages.MainNavigation
import yalexaner.messages.data.main.MainViewModel
import yalexaner.messages.ui.components.FloatingNotificationComponent

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(model: MainViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        MainNavigation()

        val visibilityState = model.floatingNotificationState.visibilityState
        AnimatedVisibility(
            visibleState = visibilityState,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            val notificationText by model.floatingNotificationState.text.observeAsState()
            FloatingNotificationComponent(text = notificationText ?: "")
        }
    }
}