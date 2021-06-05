package yalexaner.messages.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yalexaner.messages.data.messages.Message
import yalexaner.messages.data.messages.MessagesEvent.CloseOptionsMenu
import yalexaner.messages.data.messages.MessagesState
import yalexaner.messages.data.options.Option

@Composable
fun OptionsMenuComponent(
    modifier: Modifier = Modifier,
    state: MessagesState.ShowingOptionsMenu,
    onClose: (CloseOptionsMenu) -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        MessagesListComponent(state = state.messagesState)

        ShadowOverlayComponent(onClick = { onClose(CloseOptionsMenu) })

        OptionsMenu(message = state.message, options = state.options)
    }
}

@Composable
private fun OptionsMenu(
    message: Message,
    options: List<Option>
) {
    Column(modifier = Modifier) {
        MessageComponent(
            modifier = Modifier.padding(horizontal = 15.dp),
            message = message,
            textColor = Color.Black,
            surfaceColor = Color.White,
            surfaceShape = RoundedCornerShape(15.dp),
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 15.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            OptionsList(options = options)
        }
    }
}

@Composable
private fun OptionsList(
    modifier: Modifier = Modifier,
    options: List<Option>
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        options.forEach { option ->
            OptionsItem(modifier = Modifier.fillMaxWidth(), option = option)
        }
    }
}

@Composable
private fun OptionsItem(
    modifier: Modifier = Modifier,
    option: Option
) {
    Row(
        modifier = modifier
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