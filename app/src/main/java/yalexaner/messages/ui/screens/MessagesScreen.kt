package yalexaner.messages.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import yalexaner.messages.data.messages.MessagesState.*
import yalexaner.messages.data.messages.MessagesViewModel
import yalexaner.messages.data.messages.OptionsMenuState
import yalexaner.messages.ui.components.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MessagesScreen(model: MessagesViewModel) {
    val optionsMenuState = model.optionsMenuState.observeAsState(OptionsMenuState.Hiding).value

    when (val state = model.state.observeAsState().value) {
        is Loading -> LoadingComponent(modifier = Modifier.fillMaxSize())
        is ShowingNothing -> EmptyComponent(modifier = Modifier.fillMaxSize())

        is ShowingMessages -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                MessagesListComponent(state = state, onItemClick = model::obtain)

                if (optionsMenuState is OptionsMenuState.Showing) {
                    ShadowOverlayComponent(onClick = optionsMenuState.onClose)
                }

                AnimatedVisibility(visible = optionsMenuState is OptionsMenuState.Showing) {
                    if (optionsMenuState is OptionsMenuState.Showing) {
                        OptionsMenuComponent(state = optionsMenuState)
                    }
                }
            }
        }
    }
}