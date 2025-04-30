package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.accessibility.Accessibility.NEGATIVE_ALERT
import com.degrin.bitcoinwallet.core.accessibility.Accessibility.POSITIVE_ALERT
import com.degrin.bitcoinwallet.core.navigation.utils.browser.openUrlInBrowser
import com.degrin.bitcoinwallet.core.navigation.utils.compose.noRippleClickable
import com.degrin.bitcoinwallet.ui.components.button.BaseButton
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography

@Composable
fun WalletTransactionDialog(
    isSuccessState: Boolean = false,
    id: String? = null,
    onButtonClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(Sizes.Paddings.dp24)
            .clip(RoundedCornerShape(Sizes.CornerShape.dp10))
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(paddingValues = PaddingValues(Sizes.Paddings.dp16)),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = when {
                    isSuccessState -> R.drawable.ic_positive_alert
                    else -> R.drawable.ic_error_alert
                }
            ),
            contentDescription = when {
                isSuccessState -> POSITIVE_ALERT
                else -> NEGATIVE_ALERT
            }
        )
        Text(
            text = stringResource(
                id = when {
                    isSuccessState -> R.string.transaction_success_dialog_title
                    else -> R.string.transaction_error_dialog_title
                }
            ),
            style = getAppThemeTypography().displayLarge,
        )

        if (id != null && isSuccessState) {
            Text(
                modifier = Modifier.noRippleClickable {
                    val url = "https://mempool.space/signet/tx/${id}"
                    context.openUrlInBrowser(url)
                },
                text = stringResource(
                    R.string.transaction_dialog_subtitle,
                    id
                ),
                style = getAppThemeTypography().displayMedium.copy(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ),
                textAlign = TextAlign.Center,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSuccessState) {
                BaseButton(
                    buttonTextId = R.string.transaction_dialog_buttons_see_title,
                    radius = Sizes.CornerShape.dp10,
                    onClick = onButtonClick,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    textColor = MaterialTheme.colorScheme.tertiary,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            BaseButton(
                buttonTextId = when {
                    isSuccessState -> R.string.transaction_dialog_buttons_send_title
                    else -> R.string.transaction_dialog_buttons_retry_title
                },
                radius = Sizes.CornerShape.dp10,
                onClick = onButtonClick,
            )
        }
    }
}

@Preview(name = "WalletTransactionDialog", showBackground = true)
@Composable
private fun PreviewWalletTransactionDialog() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp12)) {
            listOf(true, false).forEach { isSuccess ->
                WalletTransactionDialog(
                    isSuccessState = isSuccess,
                    id = "1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef",
                    onButtonClick = {}
                )
            }
        }
    }
}
