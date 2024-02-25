package com.github.compose.chat.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.compose.chat.data.model.ChatMessage
import com.github.compose.chat.data.model.HomeChatItem
import com.github.compose.chat.data.model.uistate.MainUiState
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.ui.custom.DropletButtonNavBar
import com.github.compose.chat.ui.custom.IconButton
import com.github.compose.chat.ui.custom.LoadingCircle
import com.github.compose.chat.ui.custom.TextPainter
import com.github.compose.chat.ui.custom.ThemeSwitcher
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LaunchEffectTrue
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.drawRect

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current)
) {
    val uiState = mainViewModel.uiState
    LaunchEffectTrue {
        mainViewModel.loadChats()
    }
    LaunchedEffect(key1 = uiState.hasError) {
        if (uiState.hasError) {
            mainViewModel.showErrorToast(uiState.error)
            mainViewModel.updateState(
                uiState.copy(
                    hasError = false,
                    error = ""
                )
            )
        }
    }
    HomeScreenStateless(
        uiState = uiState,
        addButtonClick = {
            mainViewModel.navigateTo(NavTarget.Main.Contact)
            /*
              val coroutineScope = rememberCoroutineScope()
              val authManager = LocalAuthManager.current
              val db = LocalFirebaseDb.current
              db.addContact(
                user = UserInfoModel(
                    email = "adeli21gpx@gmail.com",
                    userId = "utyttt"
                ),
                onSuccess = {

                },
                onFailure = {

                }
            )*/
            /*db.saveMessage(
                FirebaseChatItem(
                    user = UserInfoModel(
                        name = "Mohammadreza sh",
                        email = "lophjded@gmail.com"
                    ),
                    content = "Hier bjkec",
                    type = "text"
                ),
                onSuccess = {
                    loge(it)
                },
                onFailure = {

                }
            )*/
            /*coroutineScope.launch(Dispatchers.IO) {
                authManager.logOut()
                mainViewModel.navigateTo(NavTarget.Auth.Base)
            }*/
        },
        themeSwitchClick = {
            mainViewModel.toggleTheme()
        },
        tabClick = {
            mainViewModel.changeTab(it)
        }
    )
}

@Composable
fun HomeScreenStateless(
    uiState: MainUiState,
    addButtonClick: () -> Unit,
    themeSwitchClick: () -> Unit,
    tabClick: (Int) -> Unit
) {
    val sorted = uiState.chatItems.sortedBy { it.time }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 64.dp, bottom = 164.dp,
                start = 10.dp, end = 10.dp
            )
        ) {
            items(sorted) {
                HomeChatItem(homeChatItem = it)
            }
        }
        Toolbar(themeSwitchClick = themeSwitchClick)
        FloatingActionButton(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 105.dp
                )
                .align(Alignment.BottomEnd),
            containerColor = AppTheme.colors.colorChatBlue,
            shape = CircleShape,
            onClick = addButtonClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Chat,
                contentDescription = "Add contact",
                tint = Color.White
            )
        }
        DropletButtonNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedItem = uiState.currentTabIndex,
            tabClick = tabClick
        )
        if (uiState.loading){
            LoadingCircle()
        }
    }
}

@Composable
fun Toolbar(
    themeSwitchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .drawRect(AppTheme.colors.background)
            .padding(top = 20.dp, start = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "FirebaseChat",
            style = AppTheme.typography.h1,
            color = AppTheme.colors.colorChatTitleText
        )
        ThemeSwitcher(
            size = 30.dp,
            onClick = themeSwitchClick
        )
        IconButton(
            tint = AppTheme.colors.colorChatTitleText,
            imageVector = Icons.Default.Search,
            iconSize = 28.dp
        ) {

        }
    }
}

@Composable
fun HomeChatItem(
    homeChatItem: HomeChatItem
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(homeChatItem.user.photo)
                .crossfade(true)
                .build(),
            placeholder = TextPainter(
                circleColor = AppTheme.colors.colorChatBlue,
                textMeasurer = rememberTextMeasurer(),
                text = homeChatItem.user.name.toString().take(2),
                circleSize = 164f
            ),
            error = TextPainter(
                circleColor = AppTheme.colors.colorChatBlue,
                textMeasurer = rememberTextMeasurer(),
                text = homeChatItem.user.name.toString().take(2),
                circleSize = 164f
            ),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(5.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = homeChatItem.user.name.toString(),
                style = AppTheme.typography.h1,
                color = AppTheme.colors.colorChatTitleText
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = (homeChatItem.content as ChatMessage.Text).content,
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.colorChatSubTitleText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column {
            Text(
                modifier = Modifier.padding(5.dp),
                text = homeChatItem.time,
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.colorChatSubTitleText
            )
        }
    }
}