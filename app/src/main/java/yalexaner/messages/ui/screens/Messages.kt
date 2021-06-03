package yalexaner.messages.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yalexaner.messages.data.Option
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessageType
import yalexaner.messages.data.messages.MessagesEvent
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.models.MessagesViewModel
import yalexaner.messages.other.noRippleClickable
import yalexaner.messages.other.noRippleCombinedClickable
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
        is MessagesState.Loaded -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                MessagesList(
                    messages = (state as MessagesState.Loaded).messages,
                    listState = (state as MessagesState.Loaded).savedListState,
                    onItemClick = { message, saveListState ->
                        model.obtain(intent = MessagesEvent.ShowOptionsMenu(message, saveListState))
                    }
                )
            }
        }
        is MessagesState.ShowOptionsMenu -> {
            val messages = (state as MessagesState.ShowOptionsMenu).messages
            val message = (state as MessagesState.ShowOptionsMenu).message
            val options = (state as MessagesState.ShowOptionsMenu).options
            val listState = (state as MessagesState.ShowOptionsMenu).savedListState

            Box(modifier = Modifier.fillMaxSize()) {
                MessagesList(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomCenter),
                    messages = messages,
                    listState = listState
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xCC000000))
                        .noRippleClickable { model.obtain(intent = MessagesEvent.CloseOptionsMenu) },
                )

                OptionsMenu(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    message = message,
                    options = options
                )
            }
        }
    }
}

@Composable
fun OptionsMenu(
    modifier: Modifier = Modifier,
    message: Message,
    options: List<Option>
) {
    Column(modifier = modifier) {
        MessageItem(message = message)

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(horizontal = 32.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = option.icon, contentDescription = "Option icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option.text, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun MessagesList(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    listState: LazyListState?,
    onItemClick: ((Message, LazyListState) -> Unit)? = null,
    onItemLongClick: (() -> Unit)? = null,
    onItemDoubleClick: (() -> Unit)? = null
) {
    val messagesByDate = messages.groupBy { Date(it.date).toFormattedString("d MMM YYY") }
    val lazyListState =
        listState ?: rememberLazyListState(messages.size * messagesByDate.size)

    LazyColumn(modifier = modifier, state = lazyListState) {
        @Suppress("NAME_SHADOWING")
        for ((date, messages) in messagesByDate) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = date,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(messages) { index, message ->
                MessageItem(
                    message = message,
                    onItemClick = { onItemClick?.invoke(it, lazyListState) },
                    onItemLongClick = onItemLongClick,
                    onItemDoubleClick = onItemDoubleClick
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
private fun MessageItem(
    modifier: Modifier = Modifier,
    message: Message,
    onItemClick: ((Message) -> Unit)? = null,
    onItemLongClick: (() -> Unit)? = null,
    onItemDoubleClick: (() -> Unit)? = null
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

        val clickable = if (onItemClick != null) {
            modifier.noRippleCombinedClickable(
                onClick = { onItemClick(message) },
                onLongClick = onItemLongClick,
                onDoubleClick = onItemDoubleClick
            )
        } else {
            modifier
        }

        Column(
            modifier = clickable
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