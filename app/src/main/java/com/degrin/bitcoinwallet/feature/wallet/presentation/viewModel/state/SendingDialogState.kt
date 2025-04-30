package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state

sealed interface SendingDialogState {
    data object None : SendingDialogState
    data object Success : SendingDialogState
    data class Error(val message: String?) : SendingDialogState
}
