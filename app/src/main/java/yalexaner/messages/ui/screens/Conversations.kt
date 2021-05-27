package yalexaner.messages.ui.screens

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import yalexaner.messages.MainActivity.Companion.LocalAppCompatActivity
import yalexaner.messages.MainActivity.Companion.LocalPermissionHandler
import yalexaner.messages.data.conversations.Conversation
import yalexaner.messages.data.conversations.ConversationsEvent
import yalexaner.messages.data.conversations.ConversationsState
import yalexaner.messages.models.ConversationsViewModel
import yalexaner.messages.models.ConversationsViewModelFactory
import yalexaner.messages.other.PERMISSION_REQUEST_CODE
import yalexaner.messages.other.toFormattedString
import yalexaner.messages.permission.PermissionHandler
import yalexaner.messages.permission.PermissionsRequest
import yalexaner.messages.ui.components.SecondRowText

@Composable
fun ConversationsScreen() {
    val permissionHandler = PermissionHandler(LocalAppCompatActivity.current)

    CompositionLocalProvider(LocalPermissionHandler provides permissionHandler) {
        PermissionsRequest(
            permissions = arrayOf(Manifest.permission.READ_SMS),
            requestCode = PERMISSION_REQUEST_CODE,
            onGranted = { Conversations() },
            onDenied = {}
        )
    }
}

@Composable
private fun Conversations() {
    val context = LocalContext.current
    val model: ConversationsViewModel = viewModel(factory = ConversationsViewModelFactory(context))

    val state by model.state.observeAsState()

    when (state) {
        is ConversationsState.Loading -> TODO()
        is ConversationsState.LoadedNothing -> TODO()
        is ConversationsState.Loaded -> {
            val conversations = (state as ConversationsState.Loaded).conversations
            List(
                conversations = conversations,
                onItemClick = { model.obtain(intent = ConversationsEvent.OpenMessagesScreen(it)) }
            )
        }
    }
}

@Composable
private fun List(
    conversations: List<Conversation>,
    onItemClick: (Int) -> Unit = {}
) {
    LazyColumn {
        items(conversations) { conversation ->
            ListItem(
                image = Icons.TwoTone.AccountCircle,
                conversation = conversation,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun ListItem(
    image: ImageVector,
    conversation: Conversation,
    onItemClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(conversation.threadId) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val cornerText = conversation.date.toFormattedString("dd.MM.yyyy")

        Image(image = image)
        Column {
            FirstRowText(text = conversation.address, cornerText = cornerText)
            Spacer(modifier = Modifier.height(4.dp))
            SecondRowText(text = conversation.body)
        }
    }
}