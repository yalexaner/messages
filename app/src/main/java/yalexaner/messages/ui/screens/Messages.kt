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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessageType
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.models.MessagesViewModel
import yalexaner.messages.other.toFormattedString
import java.util.*

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

    val isInbox = message.type == MessageType.INBOX

    val paddingStart = if (isInbox) 24.dp else 64.dp
    val paddingEnd = if (isInbox) 64.dp else 24.dp
    val alignment = if (isInbox) Arrangement.Start else Arrangement.End

    Row(
        modifier = modifier
            .padding(start = paddingStart, end = paddingEnd)
            .fillMaxWidth(),
        horizontalArrangement = alignment
    ) {
        val colors = MaterialTheme.colors
        val color = if (isInbox) colors.secondary else colors.primary
        val messageShape = RoundedCornerShape(
            topStart = 15.dp,
            topEnd = 15.dp,
            bottomStart = if (isInbox) 0.dp else 15.dp,
            bottomEnd = if (isInbox) 15.dp else 0.dp,
        )

        Column(
            modifier = modifier
                .background(color = color, shape = messageShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = message.body,
                style = MaterialTheme.typography.subtitle1,
                color = if (isInbox) colors.onSecondary else colors.onPrimary
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = Date(message.date).toFormattedString("h:mm a"),
                style = MaterialTheme.typography.overline,
                color = if (isInbox) colors.onSecondary else colors.onPrimary
            )
        }
    }
}