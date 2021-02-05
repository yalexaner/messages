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
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import yalexaner.messages.R
import yalexaner.messages.Route
import yalexaner.messages.data.Conversation
import yalexaner.messages.data.MainViewModel
import yalexaner.messages.data.MainViewModelFactory
import yalexaner.messages.other.AmbientAppCompatActivity
import yalexaner.messages.other.AmbientPermissionHandler
import yalexaner.messages.other.PERMISSION_REQUEST_CODE
import yalexaner.messages.permission.PermissionHandler
import yalexaner.messages.permission.PermissionsRequest
import yalexaner.messages.ui.theme.indianRed

@Composable
fun ConversationsScreen(navController: NavController) {
    val permissionHandler = PermissionHandler(AmbientAppCompatActivity.current)

    Providers(AmbientPermissionHandler provides permissionHandler) {
        PermissionsRequest(
            permissions = arrayOf(Manifest.permission.READ_SMS),
            requestCode = PERMISSION_REQUEST_CODE,
            onGranted = { Conversations() },
            onDenied = { navController.navigate(Route.MAIN_SCREEN) },
            rational = { navController.navigate(Route.MAIN_SCREEN) }
        )
    }
}

@Composable
private fun Conversations() {
    val conversations by run {
        val context = AmbientContext.current
        val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context))

        viewModel.conversations.observeAsState()
    }
    val loadingMessage = stringResource(R.string.loading)
    val noConversationMessage = stringResource(R.string.no_conversations)

    if (conversations.isNullOrEmpty()) {
        Message(text = if (conversations == null) loadingMessage else noConversationMessage)
    } else {
        List(conversations = conversations!!)
    }
}

@Composable
private fun Message(text: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
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
                cornerText = conversation.date.toString()
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
        ListItem.Image(image = image)
        Column {
            ListItem.FirstRowText(text = text, cornerText = cornerText)
            Spacer(modifier = Modifier.height(4.dp))
            ListItem.SecondRowText(text = secondText)
        }
    }
}

private object ListItem {
    @Composable
    fun Image(image: ImageVector) {
        Image(
            imageVector = image,
            colorFilter = ColorFilter.tint(color = indianRed),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(72.dp)
        )
    }

    @Composable
    fun FirstRowText(text: String, cornerText: String) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        )
    }
}