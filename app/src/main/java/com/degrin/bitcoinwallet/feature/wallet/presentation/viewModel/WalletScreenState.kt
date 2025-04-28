package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

sealed interface WalletScreenState {
    data object None : WalletScreenState
    data object Loading : WalletScreenState
    data object Error : WalletScreenState
    data object Data : WalletScreenState
}
