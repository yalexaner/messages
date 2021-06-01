package yalexaner.messages.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessageType
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.models.MessagesViewModel

@Composable
fun MessagesScreen(model: MessagesViewModel) {
    Messages(model = model)
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
        itemsIndexed(messages) { index, message ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Message(message = message)

            if (message.type == messages.getOrNull(index + 1)?.type) {
                Spacer(modifier = Modifier.height(2.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    message: Message
) {
    if (message.body.isBlank()) return

    val startPadding: Dp
    val endPadding: Dp
    val alignment: Alignment.Horizontal
    val color: Color
    val textColor: Color

    when (message.type) {
        MessageType.INBOX -> {
            startPadding = 24.dp
            endPadding = 64.dp
            alignment = Alignment.Start
            color = MaterialTheme.colors.secondary
            textColor = MaterialTheme.colors.onSecondary
        }

        MessageType.OUTBOX -> {
            startPadding = 64.dp
            endPadding = 24.dp
            alignment = Alignment.End
            color = MaterialTheme.colors.primary
            textColor = MaterialTheme.colors.onPrimary
        }
    }

    Column(
        modifier = modifier
            .padding(start = startPadding, end = endPadding)
            .fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Text(
            modifier = Modifier
                .background(color = color, shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = message.body,
            color = textColor
        )
    }
}