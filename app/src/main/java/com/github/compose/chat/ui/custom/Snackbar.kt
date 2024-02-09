package com.github.compose.chat.ui.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.github.compose.chat.data.model.DialogType
import com.github.compose.chat.data.model.ToastData
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.theme.Blue
import com.github.compose.chat.ui.theme.Green
import com.github.compose.chat.ui.theme.Red
import com.github.compose.chat.ui.theme.WhiteBackground
import com.github.compose.chat.utils.drawRect

@Composable
fun CustomSnackBar(toastData: ToastData) {
    when (toastData.type) {
        DialogType.SUCCESS -> {
            Snack(
                message = toastData.message,
                containerColor = Green,
                icon = Icons.Outlined.Done
            )
        }

        DialogType.FAIL -> {
            Snack(
                message = toastData.message,
                containerColor = Red,
                icon = Icons.Outlined.Error
            )
        }

        DialogType.INFO -> {
            Snack(
                message = toastData.message,
                containerColor = Blue,
                icon = Icons.Outlined.Info
            )
        }

        DialogType.WARNING -> {
            Snack(
                message = toastData.message,
                containerColor = Red,
                icon = Icons.Outlined.Info
            )
        }
    }
}

@Composable
internal fun Snack(
    message: String,
    containerColor: Color,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = 70.dp)
            .drawRect(color = containerColor)
            .padding(vertical = 15.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier,
            imageVector = icon,
            tint = Color.White,
            contentDescription = "Toast Icon",
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            text = message,
            color = WhiteBackground,
            style = AppTheme.typography.body,
            maxLines = 4
        )
    }
}