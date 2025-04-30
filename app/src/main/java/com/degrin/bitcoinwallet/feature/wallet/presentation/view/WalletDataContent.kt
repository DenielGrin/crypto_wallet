package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.InputFieldsState
import com.degrin.bitcoinwallet.ui.components.header.WalletHeader
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import java.math.BigDecimal

@Composable
fun WalletDataContent(
    balance: BigDecimal,
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
        WalletHeader(
            headerId = R.string.wallet_screen_title,
            balance = balance,
        )

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