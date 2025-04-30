package com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel

import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData

sealed interface TransactionScreenState {
    data object None : TransactionScreenState
    data object Loading : TransactionScreenState
    data object Error : TransactionScreenState
    data object NullableData : TransactionScreenState
    data class Data(val data: List<TransactionSortedData>) : TransactionScreenState
}
