package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.WalletScreenState
import com.degrin.bitcoinwallet.ui.components.button.BaseButton
import com.degrin.bitcoinwallet.ui.components.errors.BaseErrorContainer
import com.degrin.bitcoinwallet.ui.components.inputField.BaseTextField
import com.degrin.bitcoinwallet.ui.components.progress.BasePulseLoader
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography
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