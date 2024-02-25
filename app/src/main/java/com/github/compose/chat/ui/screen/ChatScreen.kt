package com.github.compose.chat.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.compose.chat.data.model.ChatMessage
import com.github.compose.chat.ui.custom.chat.ChatBox
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.viewmodel.FirebaseViewModel
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.drawRect

@Composable
fun ChatScreen(
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current),
    firebaseModel: FirebaseViewModel,
    targetEmail: String
) {
    val listState = rememberLazyListState()
    val chatItems = firebaseModel.messageList
    LaunchedEffect(chatItems.size) {
        listState.animateScrollToItem(chatItems.size)
    }
    ChatScreenStateless(
        data = chatItems,
        state = listState,
        onSendChatClickListener = {
            sendTextMessage(
                firebaseModel = firebaseModel,
                content = it
            )
        }
    )
}

internal fun sendTextMessage(
    firebaseModel: FirebaseViewModel,
    content: String
) {
    firebaseModel.sendTextMessage(content)
}

@Composable
fun ChatScreenStateless(
    data: SnapshotStateList<ChatMessage>,
    state: LazyListState,
    onSendChatClickListener: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 70.dp, start = 5.dp, end = 5.dp)
        ) {
            items(data) { item ->
                ChatItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    message = item
                )
            }
        }
        ChatBox(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .drawRect(color = AppTheme.colors.background)
                .padding(5.dp),
            onSendChatClickListener = onSendChatClickListener,
        )
    }
}

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    message: ChatMessage
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .background(AppTheme.colors.colorChatTitleText)
                .padding(16.dp)
                .align(Alignment.End)
        ) {
            Text(
                text = (message as ChatMessage.Text).content,
                color = AppTheme.colors.background
            )
        }
    }
}