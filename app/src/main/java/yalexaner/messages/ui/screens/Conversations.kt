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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import yalexaner.messages.MainActivity.Companion.LocalAppCompatActivity
import yalexaner.messages.MainActivity.Companion.LocalPermissionHandler
import yalexaner.messages.data.ConversationsViewModel
import yalexaner.messages.data.ConversationsViewModelFactory
import yalexaner.messages.data.enteties.Conversation
import yalexaner.messages.other.PERMISSION_REQUEST_CODE
import yalexaner.messages.other.toFormattedString
import yalexaner.messages.permission.PermissionHandler
import yalexaner.messages.permission.PermissionsRequest
import yalexaner.messages.ui.theme.indianRed

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
    val conversations by run {
        val context = LocalContext.current
        val viewModel: ConversationsViewModel =
            viewModel(factory = ConversationsViewModelFactory(context))

        viewModel.conversations.observeAsState()
    }

    if (!conversations.isNullOrEmpty()) {
        List(conversations = conversations!!)
    }
}

@Composable
private fun List(conversations: List<Conversation>) {
    LazyColumn {
        items(conversations) { conversation ->
            ListItem(
                image = Icons.TwoTone.AccountCircle,
                text = conversation.address,
                secondText = conversation.body,
                cornerText = conversation.date.toFormattedString("dd.MM.yyyy")
            )
        }
    }
}

@Composable
private fun ListItem(
    image: ImageVector,
    text: String,
    secondText: String,
    cornerText: String
) {
    Row(
        modifier = Modifier
            .clickable { }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(image = image)
        Column {
            FirstRowText(text = text, cornerText = cornerText)
            Spacer(modifier = Modifier.height(4.dp))
            SecondRowText(text = secondText)
        }
    }
}

@Composable
fun Image(image: ImageVector) {
    Image(
        imageVector = image,
        colorFilter = ColorFilter.tint(color = indianRed),
        contentDescription = null,
        modifier = Modifier
            .size(64.dp)
            .padding(end = 8.dp)
    )
}

@Composable
fun FirstRowText(text: String, cornerText: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )
        Text(
            text = cornerText,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            ),
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SecondRowText(text: String) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
    )
}