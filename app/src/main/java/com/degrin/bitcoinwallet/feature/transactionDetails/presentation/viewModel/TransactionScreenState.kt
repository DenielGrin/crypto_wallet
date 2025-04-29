package com.degrin.bitcoinwallet.feature.transactionDetails.presentation.viewModel

sealed interface TransactionScreenState {
    data object None : TransactionScreenState
    data object Loading : TransactionScreenState
    data object Error : TransactionScreenState
    data object Data : TransactionScreenState
}
