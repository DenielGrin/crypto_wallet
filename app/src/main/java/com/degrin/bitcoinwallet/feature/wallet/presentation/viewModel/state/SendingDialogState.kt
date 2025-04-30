package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state

sealed interface SendingDialogState {
    data object None : SendingDialogState
    data class Success(val id: String) : SendingDialogState
    data class Error(val message: String?) : SendingDialogState
}
