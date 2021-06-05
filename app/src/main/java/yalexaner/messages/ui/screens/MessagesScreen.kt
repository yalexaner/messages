package yalexaner.messages.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import yalexaner.messages.data.messages.MessagesState.*
import yalexaner.messages.data.messages.MessagesViewModel
import yalexaner.messages.ui.components.MessagesListComponent
import yalexaner.messages.ui.components.OptionsMenuComponent

@Composable
fun MessagesScreen(model: MessagesViewModel) {
    when (val state = model.state.observeAsState().value) {
        is Loading -> Text(text = "Loading")
        is ShowingNothing -> Text(text = "Nothing to show")
        is ShowingMessages -> MessagesListComponent(state = state) { model.obtain(intent = it) }
        is ShowingOptionsMenu -> OptionsMenuComponent(state = state) { model.obtain(intent = it) }
    }
}