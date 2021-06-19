package yalexaner.messages.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import yalexaner.messages.data.messages.MessagesEvent
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.data.messages.MessagesViewModel
import yalexaner.messages.data.options.OptionsMenuState
import yalexaner.messages.ui.components.*

@Composable
fun MessagesScreen(model: MessagesViewModel) {
    when (val state = model.state.observeAsState().value) {
        is MessagesState.Loading -> LoadingStateHandler()
        is MessagesState.ShowingNothing -> ShowingNothingStateHandler()
        is MessagesState.ShowingMessages -> ShowingMessagesStateHandler(state, model)
    }
}

@Composable
private fun LoadingStateHandler() {
    LoadingComponent(modifier = Modifier.fillMaxSize())
}

@Composable
private fun ShowingNothingStateHandler() {
    EmptyComponent(modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ShowingMessagesStateHandler(
    state: MessagesState.ShowingMessages,
    model: MessagesViewModel
) {
    val optionsMenuState by model.optionsMenuState.observeAsState(OptionsMenuState())

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        MessagesListComponent(state = state, onItemClick = model::obtain)

        if (optionsMenuState.showing) {
            ShadowOverlayComponent { model.obtain(intent = MessagesEvent.CloseOptionsMenu) }
        }

        AnimatedVisibility(visible = optionsMenuState.showing) {
            OptionsMenuComponent(
                message = optionsMenuState.message!!,
                options = optionsMenuState.options!!
            )
        }
    }
}