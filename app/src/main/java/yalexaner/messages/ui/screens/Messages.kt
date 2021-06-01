package yalexaner.messages.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessagesEvent
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.models.MessagesViewModel

@Composable
fun MessagesScreen(threadId: String) {
    Messages(threadId = threadId)
}

@Composable
fun Messages(
    model: MessagesViewModel = hiltViewModel()
) {
    val state by model.state.observeAsState()

    when (state) {
        is MessagesState.Loading -> Text(text = "Loading")
        is MessagesState.LoadedNothing -> Text(text = "Nothing to show")
        is MessagesState.Loaded -> MessagesList(messages = (state as MessagesState.Loaded).messages)
    }
}

@Composable
fun MessagesList(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            Text(text = message.body)
        }
    }
}