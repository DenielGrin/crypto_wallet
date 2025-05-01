package com.degrin.bitcoinwallet.feature.wallet.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.feature.wallet.presentation.view.WalletDataContent
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.InputFieldsState
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.WalletScreenState
import com.degrin.bitcoinwallet.ui.components.errors.BaseErrorContainer
import com.degrin.bitcoinwallet.ui.components.progress.BasePulseLoader
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import java.math.BigDecimal

@Composable
fun WalletContent(
    modifier: Modifier = Modifier,
    state: WalletScreenState,
    inputState: InputFieldsState,
    onUpdateAddress: (String) -> Unit,
    onUpdateAmount: (String) -> Unit,
    onSendClick: () -> Unit,
    onReloadClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is WalletScreenState.Loading -> BasePulseLoader()

            is WalletScreenState.Error -> BaseErrorContainer(
                onReloadClick = onReloadClick,
            )

            is WalletScreenState.Data -> WalletDataContent(
                balance = state.balance,
                inputState = inputState,
                onUpdateAddress = onUpdateAddress,
                onUpdateAmount = onUpdateAmount,
                onSendClick = onSendClick
            )

            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransactionContent() {
    AppTheme {
        WalletContent(
            state = WalletScreenState.Data(
                balance = BigDecimal(1234567890.0)
            ),
            inputState = InputFieldsState(),
            onUpdateAddress = {},
            onUpdateAmount = {},
            onReloadClick = {},
            onSendClick ={}
        )
    }
}