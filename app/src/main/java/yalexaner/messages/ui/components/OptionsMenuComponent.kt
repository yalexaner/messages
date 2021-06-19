package yalexaner.messages.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
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
import yalexaner.messages.data.options.Option

@Composable
fun OptionsMenuComponent(
    message: Message,
    options: List<Option>,
    closeOnAction: () -> Unit = {}
) {
    Column(modifier = Modifier) {
        SelectionContainer {
            MessageComponent(
                modifier = Modifier.padding(horizontal = 16.dp),
                message = message,
                textColor = Color.Black,
                surfaceColor = Color.White,
                surfaceShape = RoundedCornerShape(15.dp),
                alignment = Alignment.Center
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 15.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            OptionsList( options = options, onItemClick = closeOnAction )
        }
    }
}

@Composable
private fun OptionsList(
    modifier: Modifier = Modifier,
    options: List<Option>,
    onItemClick: () -> Unit = {}
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        options.forEach { option ->
            OptionsItem(modifier = Modifier.fillMaxWidth(), option = option, onClick = onItemClick)
        }
    }
}

@Composable
private fun OptionsItem(
    modifier: Modifier = Modifier,
    option: Option,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable {
                option.action()
                onClick()
            }
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