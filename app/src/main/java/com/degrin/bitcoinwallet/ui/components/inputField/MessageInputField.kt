package com.cordobalegal.core.ui.component.inputField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.cordobalegal.R
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.cordobalegal.core.ui.theme.AppTheme
import com.cordobalegal.core.ui.theme.getAppThemeTypography
import com.cordobalegal.core.ui.theme.getThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onAttachClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Sizes.Size.dp50)
            .background(
                color = getThemeColor(
                    lightThemeColor = Color(0xFFeef7fc),
                    darkThemeColor = Color(0xFF1c3038),
                ),
                shape = RoundedCornerShape(Sizes.CornerShape.dp16)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onAttachClick) {
            Icon(
                painter = painterResource(R.drawable.ic_aatach_file),
                contentDescription = "Attach",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = getAppThemeTypography().displayMedium,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.message_hint),
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = getAppThemeTypography().displayMedium
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        IconButton(onClick = onSendClick) {
            Icon(
                painter = painterResource(R.drawable.ic_send_message),
                contentDescription = "Send",
                tint = Color(0xFF4096EE)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun _Preview() {
    AppTheme {
        MessageInputField(
            value = "",
            onValueChange = {},
            onSendClick = {},
            onAttachClick = {}
        )
    }
}