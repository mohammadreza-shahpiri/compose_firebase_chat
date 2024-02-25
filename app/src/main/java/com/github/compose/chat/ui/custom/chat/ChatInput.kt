package com.github.compose.chat.ui.custom.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.theme.Teal

@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    onSendChatClickListener: (String) -> Unit
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    val sendActive by remember {
        derivedStateOf { chatBoxValue.text.isBlank().not() }
    }
    Row(
        modifier = modifier
    ) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            textStyle = AppTheme.typography.button.copy(
                color = AppTheme.colors.background
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = AppTheme.colors.colorChatGray,
                unfocusedContainerColor = AppTheme.colors.colorChatGray,
                cursorColor = AppTheme.colors.background
            ),
            placeholder = {
                Text(
                    text = "Message",
                    color = AppTheme.colors.background
                )
            }
        )
        IconButton(
            onClick = {
                val msg = chatBoxValue.text
                if (msg.isBlank()) return@IconButton
                onSendChatClickListener(chatBoxValue.text)
                chatBoxValue = TextFieldValue("")
            },
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .size(52.dp),
            enabled = sendActive,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Teal,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                modifier = Modifier
                    .size(25.dp),
                tint = Color.White
            )
        }
    }
}