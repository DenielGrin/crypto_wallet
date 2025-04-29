package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

sealed interface WalletScreenState {
    data object None : WalletScreenState
    data object Loading : WalletScreenState

    data class Error(val message: String?) : WalletScreenState

    data class Data(val balance: Double?) : WalletScreenState
}
