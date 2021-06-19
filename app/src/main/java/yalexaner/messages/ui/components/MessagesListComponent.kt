package yalexaner.messages.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessagesEvent.ShowOptionsMenu
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.other.toFormattedString
import yalexaner.messages.ui.theme.MessagesCornersShape
import java.util.*

@Composable
fun MessagesListComponent(
    state: MessagesState.ShowingMessages,
    onItemClick: (ShowOptionsMenu) -> Unit = {}
) {
    MessagesList(
        messages = state.messages,
        listState = state.savedListPosition,
        onItemClick = onItemClick
    )
}

@Composable
private fun MessagesList(
    messages: List<Message>,
    listState: LazyListState?,
    onItemClick: (ShowOptionsMenu) -> Unit = {},
    onItemLongClick: () -> Unit = {},
) {
    val messagesByDate = messages.groupBy { Date(it.date).toFormattedString("d MMM YYY") }

    val initialFirstVisibleItemIndex = messages.size * messagesByDate.size
    val lazyListState = listState ?: rememberLazyListState(initialFirstVisibleItemIndex)

    LazyColumn(state = lazyListState) {
        @Suppress("NAME_SHADOWING")
        for ((date, messages) in messagesByDate) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    text = date,
                    textAlign = TextAlign.Center
                )
            }

            itemsIndexed(messages) { index, message ->
                MessagesItem(
                    message = message,
                    onClick = { onItemClick(ShowOptionsMenu(message, lazyListState)) },
                    onLongClick = onItemLongClick
                )

                if (message.type == messages.getOrNull(index + 1)?.type) {
                    Spacer(modifier = Modifier.height(2.dp))
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun MessagesItem(
    message: Message,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    if (message.body.isBlank()) return

    val paddingStart = when (message.type) {
        Message.Type.READONLY -> 8.dp
        Message.Type.INBOX -> 24.dp
        Message.Type.OUTBOX -> 64.dp
    }

    val paddingEnd = when (message.type) {
        Message.Type.READONLY -> 8.dp
        Message.Type.INBOX -> 64.dp
        Message.Type.OUTBOX -> 24.dp
    }

    MessageComponent(
        modifier = Modifier.padding(start = paddingStart, end = paddingEnd),

        message = message,

        textColor = when (message.type) {
            Message.Type.READONLY -> Color.Black
            Message.Type.INBOX -> MaterialTheme.colors.onSecondary
            Message.Type.OUTBOX -> MaterialTheme.colors.onPrimary
        },

        surfaceColor = when (message.type) {
            Message.Type.READONLY -> Color.White
            Message.Type.INBOX -> MaterialTheme.colors.secondary
            Message.Type.OUTBOX -> MaterialTheme.colors.primary
        },

        surfaceShape = when (message.type) {
            Message.Type.READONLY -> RectangleShape
            Message.Type.INBOX -> MessagesCornersShape.INBOX
            Message.Type.OUTBOX -> MessagesCornersShape.OUTBOX
        },

        alignment = when (message.type) {
            Message.Type.READONLY, Message.Type.INBOX -> Alignment.BottomStart
            Message.Type.OUTBOX -> Alignment.BottomEnd
        },

        onClick = { onClick() },
        onLongClick = { onLongClick() }
    )
}