package yalexaner.messages.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessagesEvent.ShowOptionsMenu
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.other.toFormattedString
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

    val isInbox = message.type == Message.Type.INBOX

    val paddingStart = if (isInbox) 24.dp else 64.dp
    val paddingEnd = if (isInbox) 64.dp else 24.dp
    val colors = MaterialTheme.colors

    val messageShape = RoundedCornerShape(
        topStart = 15.dp,
        topEnd = 15.dp,
        bottomStart = if (isInbox) 0.dp else 15.dp,
        bottomEnd = if (isInbox) 15.dp else 0.dp,
    )

    MessageComponent(
        modifier = Modifier.padding(start = paddingStart, end = paddingEnd),
        message = message,
        textColor = if (isInbox) colors.onSecondary else colors.onPrimary,
        surfaceColor = if (isInbox) colors.secondary else colors.primary,
        surfaceShape = messageShape,
        alignment = if (isInbox) Alignment.BottomStart else Alignment.BottomEnd,
        onClick = { onClick() },
        onLongClick = { onLongClick() }
    )
}