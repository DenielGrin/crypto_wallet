package com.degrin.bitcoinwallet.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography

@Composable
fun BaseRoundedButton(
    modifier: Modifier = Modifier,
    iconId: Int = R.drawable.ic_arrow_up,
    textId: Int,
    onClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp4),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            modifier = Modifier.size(Sizes.Size.dp40),
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
        ) {
            Icon(
                modifier = Modifier.size(Sizes.Size.dp40),
                painter = painterResource(id = iconId),
                contentDescription = ""
            )

        }
        Text(
            text = stringResource(id = textId),
            style = getAppThemeTypography().titleSmall.copy(
                color = MaterialTheme.colorScheme.onTertiary
            )
        )
    }
}

@Preview(name = "BaseRoundedButton")
@Composable
private fun PreviewBaseRoundedButton() {
    AppTheme {
        BaseRoundedButton(
            iconId = R.drawable.ic_arrow_up,
            textId = R.string.transaction_dialog_buttons_send_title,
            onClick = {}
        )
    }
}
