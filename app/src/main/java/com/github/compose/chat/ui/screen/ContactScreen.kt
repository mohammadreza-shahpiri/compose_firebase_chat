package com.github.compose.chat.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.github.compose.chat.R
import com.github.compose.chat.data.model.UserInfoModel
import com.github.compose.chat.data.model.uistate.ContactUiState
import com.github.compose.chat.ui.custom.IconButton
import com.github.compose.chat.ui.custom.LoadingCircle
import com.github.compose.chat.ui.custom.ProgressButton
import com.github.compose.chat.ui.custom.TextPainter
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.theme.Teal
import com.github.compose.chat.ui.theme.colorDarkBackground
import com.github.compose.chat.ui.viewmodel.ContactViewModel
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LaunchEffectTrue
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.LocalAuthManager
import com.github.compose.chat.utils.LocalFirebaseDb
import com.github.compose.chat.utils.ToastManager
import com.github.compose.chat.utils.drawRect

@Composable
fun ContactScreen(
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current),
    contactViewModel: ContactViewModel
) {
    val uiState = contactViewModel.uiState
    LaunchEffectTrue {
        contactViewModel.loadContacts()
    }
    LaunchedEffect(key1 = uiState.hasError) {
        if (uiState.hasError) {
            ToastManager.showErrorToast(uiState.error)
            contactViewModel.updateState(
                uiState.copy(
                    hasError = false,
                    error = ""
                )
            )
        }
    }
    ContactScreenStateless(
        uiState = uiState,
        onItemClick = {},
        newContactClick = {
            contactViewModel.updateState(
                uiState.copy(
                    showAddDialog = true
                )
            )
        },
        backClick = {
            mainViewModel.navigateUp()
        },
        searchClick = {

        },
        addDialogDismiss = {
            contactViewModel.updateState(
                uiState.copy(
                    showAddDialog = false
                )
            )
        }
    )
}


@Composable
fun ContactScreenStateless(
    uiState: ContactUiState,
    onItemClick: (contact: UserInfoModel) -> Unit,
    newContactClick: () -> Unit,
    addDialogDismiss: () -> Unit,
    backClick: () -> Unit,
    searchClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 76.dp, horizontal = 10.dp)
        ) {
            item {
                NewContact(onClick = newContactClick)
            }
            item {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp),
                    text = "Contacts of Firebase",
                    color = AppTheme.colors.colorChatSubTitleText
                )
            }
            items(uiState.contacts) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onItemClick(it)
                    },
                    color = Color.Transparent
                ) {
                    ContactItem(contact = it)
                }
            }
        }
        if (uiState.loading) {
            LoadingCircle()
        }
        if (uiState.showAddDialog) {
            AddContactDialog(
                uiState = uiState,
                contactAdd = {
                    uiState.contacts.add(it)
                    addDialogDismiss()
                },
                onDismiss = addDialogDismiss
            )
        }
        ContactToolbar(
            contactSize = uiState.contacts.size,
            backClick = backClick,
            searchClick = searchClick
        )
    }
}

@Composable
fun NewContact(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            color = Teal,
            shape = CircleShape
        ) {
            Image(
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp),
                painter = rememberAsyncImagePainter(model = R.drawable.add_contact),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Text(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            text = "New contact",
            style = AppTheme.typography.body,
            color = AppTheme.colors.colorChatTitleText
        )
    }
}

@Composable
fun ContactToolbar(
    contactSize: Int,
    backClick: () -> Unit,
    searchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .drawRect(AppTheme.colors.background)
            .padding(top = 20.dp, start = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.padding(end = 5.dp),
            tint = AppTheme.colors.colorChatTitleText,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            iconSize = 28.dp,
            onClick = backClick
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Select contact",
                style = AppTheme.typography.h1,
                color = AppTheme.colors.colorChatTitleText
            )
            Text(
                text = "$contactSize contacts",
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.colorChatSubTitleText
            )
        }
        IconButton(
            tint = AppTheme.colors.colorChatTitleText,
            imageVector = Icons.Default.Search,
            iconSize = 28.dp,
            onClick = searchClick
        )
    }
}

@Composable
fun ContactItem(
    contact: UserInfoModel
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
                .data(contact.photo)
                .crossfade(true)
                .build(),
            placeholder = TextPainter(
                circleColor = AppTheme.colors.colorChatBlue,
                textMeasurer = rememberTextMeasurer(),
                text = contact.name.toString().take(2),
                circleSize = 164f
            ),
            error = TextPainter(
                circleColor = AppTheme.colors.colorChatBlue,
                textMeasurer = rememberTextMeasurer(),
                text = contact.name.toString().take(2),
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
                text = contact.name.toString(),
                style = AppTheme.typography.h1,
                color = AppTheme.colors.colorChatTitleText
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = contact.email.toString(),
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.colorChatSubTitleText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AddContactDialog(
    uiState: ContactUiState,
    contactAdd: (UserInfoModel) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val db = LocalFirebaseDb.current
    val authManager = LocalAuthManager.current
    fun findContact() {
        if (authManager.getCurrentUser()?.email == text) {
            ToastManager.showErrorToast("You can't add yourself!")
            return
        }
        if (uiState.contacts.fastAny { it.email == text }) {
            ToastManager.showErrorToast("Contact already exist!")
            return
        }
        keyboardController?.hide()
        loading = true
        db.findContact(
            email = text,
            onSuccess = { user ->
                loading = false
                if (user != null) {
                    db.addContact(
                        user = user,
                        onSuccess = {
                            contactAdd(user)
                        },
                        onFailure = {
                            ToastManager.showErrorToast(it)
                        }
                    )
                } else {
                    ToastManager.showErrorToast("User not found")
                }
            },
            onFailure = {
                loading = false
                ToastManager.showErrorToast(it)
            }
        )
    }
    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.8f),
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismiss,
        containerColor = AppTheme.colors.colorChatTitleText,
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Close,
                    tint = AppTheme.colors.colorChatGray,
                    onClick = onDismiss
                )
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = "Add new contact",
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.background
                )
            }
        },
        text = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                placeholder = {
                    Text(text = "email...")
                },
                value = text,
                enabled = !loading,
                onValueChange = { text = it },
                textStyle = TextStyle(
                    color = colorDarkBackground
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
        },
        confirmButton = {
            ProgressButton(
                text = "Add contact",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                isLoading = loading,
                onClick = {
                    findContact()
                }
            )
        }
    )
}