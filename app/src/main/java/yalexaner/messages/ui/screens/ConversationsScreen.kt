package yalexaner.messages.ui.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yalexaner.messages.MainActivity.Companion.LocalAppCompatActivity
import yalexaner.messages.MainActivity.Companion.LocalPermissionHandler
import yalexaner.messages.data.conversations.Conversation
import yalexaner.messages.data.conversations.ConversationsState.*
import yalexaner.messages.data.conversations.ConversationsViewModel
import yalexaner.messages.other.PERMISSION_REQUEST_CODE
import yalexaner.messages.other.permission.PermissionHandler
import yalexaner.messages.other.permission.PermissionsRequest
import yalexaner.messages.other.toFormattedString
import yalexaner.messages.ui.components.LoadingComponent
import yalexaner.messages.ui.theme.MessagesTextStyle
import yalexaner.messages.ui.theme.indianRed

@Composable
fun ConversationsScreen(onItemClick: (String) -> Unit) {
    val permissionHandler = PermissionHandler(LocalAppCompatActivity.current)

    CompositionLocalProvider(LocalPermissionHandler provides permissionHandler) {
        PermissionsRequest(
            permissions = arrayOf(Manifest.permission.READ_SMS),
            requestCode = PERMISSION_REQUEST_CODE,
            onGranted = { Conversations(onItemClick = onItemClick) },
            onDenied = {}
        )
    }
}

@Composable
private fun Conversations(
    model: ConversationsViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    when (val state = model.state.observeAsState().value) {
        is Loading -> LoadingComponent(modifier = Modifier.fillMaxSize(), message = "Loading")
        is ShowingNothing -> Text(text = "Nothing loaded")
        is ShowingConversations -> ConversationsList(state.conversations, onItemClick)
    }
}

@Composable
private fun ConversationsList(
    conversations: List<Conversation>,
    onItemClick: (String) -> Unit = {}
) {
    LazyColumn {
        items(conversations) { conversation ->
            ConversationsItem(
                image = Icons.TwoTone.AccountCircle,
                conversation = conversation,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun ConversationsItem(
    image: ImageVector,
    conversation: Conversation,
    onItemClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(conversation.threadId) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val date = conversation.date.toFormattedString("dd.MM.yyyy")

        ConversationsItemImage(image = image)

        Column {
            ConversationsItemAddress(text = conversation.address, date = date)
            Spacer(modifier = Modifier.height(4.dp))
            ConversationsItemSnippet(text = conversation.snippet)
        }
    }
}

@Composable
private fun ConversationsItemImage(image: ImageVector) {
    Image(
        modifier = Modifier
            .size(64.dp)
            .padding(end = 8.dp),
        imageVector = image,
        colorFilter = ColorFilter.tint(color = indianRed),
        contentDescription = "Conversation icon",
    )
}

@Composable
private fun ConversationsItemAddress(text: String, date: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MessagesTextStyle.conversationsAddress
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = date,
            style = MessagesTextStyle.conversationsDate,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun ConversationsItemSnippet(text: String) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MessagesTextStyle.conversationsSnippet
    )
}