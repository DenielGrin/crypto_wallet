package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.InputFieldsState
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography
import java.math.BigDecimal

@Composable
fun WalletDataContent(
    balance: BigDecimal?,
    inputState: InputFieldsState,
    onUpdateAddress: (String) -> Unit,
    onUpdateAmount: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(Sizes.Paddings.dp16)),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp16)
    ) {
        Text(
            text = stringResource(R.string.wallet_screen_title),
            style = getAppThemeTypography().bodyLarge,
        )
        Box(
            modifier = Modifier
                .border(
                    shape = CircleShape,
                    border = BorderStroke(
                        width = Sizes.BorderSizes.dp1,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .padding(paddingValues = PaddingValues(Sizes.Paddings.dp16)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(
                    R.string.wallet_screen_subtitle,
                    balance?.toPlainString().orEmpty()
                ),
                style = getAppThemeTypography().bodyMedium,
            )
        }


        WalletInputFields(
            modifier = Modifier.fillMaxWidth(),
            inputState = inputState,
            onUpdateAddress = onUpdateAddress,
            onUpdateAmount = onUpdateAmount,
            onSendClick = onSendClick
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransactionContent() {
    AppTheme {
        WalletDataContent(
            balance = BigDecimal(1234567890.0),
            inputState = InputFieldsState(),
            onUpdateAddress = {},
            onUpdateAmount = {},
            onSendClick = {}
        )
    }
}