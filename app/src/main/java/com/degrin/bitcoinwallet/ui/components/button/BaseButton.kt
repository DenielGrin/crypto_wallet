package com.degrin.bitcoinwallet.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    buttonTextId: Int,
    enabled: Boolean = true,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    radius: Dp = Sizes.CornerShape.dp10,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.background,
    textStyle: TextStyle = getAppThemeTypography().headlineMedium,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        border = border,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(size = radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            text = stringResource(id = buttonTextId),
            color = textColor,
            style = textStyle,
        )
    }
}

@Preview(name = "BaseButton")
@Composable
private fun PreviewBaseButton() {
    AppTheme {
        BaseButton(
            buttonTextId = R.string.wallet_screen_button_send_title,
            onClick = {}
        )
    }
}