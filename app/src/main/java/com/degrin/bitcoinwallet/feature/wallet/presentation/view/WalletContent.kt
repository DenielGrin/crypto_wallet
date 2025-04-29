package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.WalletScreenState
import com.degrin.bitcoinwallet.ui.components.progress.BaseLottieLoader
import com.degrin.bitcoinwallet.ui.components.progress.BasePulseLoader
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun WalletContent(
    modifier: Modifier = Modifier,
    state: WalletScreenState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is WalletScreenState.Loading -> BasePulseLoader()

            is WalletScreenState.Error -> {
                // Handle error state
            }

            is WalletScreenState.Data -> {
                // Handle data state
            }

            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransactionContent() {
    AppTheme {
        WalletContent(
            state = WalletScreenState.Loading
        )
    }
}