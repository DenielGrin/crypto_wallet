package com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.degrin.bitcoinwallet.feature.transactions.domain.useCase.TransactionUseCase

class TransactionsViewModel(
    private val useCase: TransactionUseCase
) : ViewModel() {

    var viewModelState by mutableStateOf<TransactionScreenState>(TransactionScreenState.None)
        private set

    init {
        updateState(TransactionScreenState.Loading)
        getData()
    }

    private fun getData() {

    }

    private fun updateState(value: TransactionScreenState) {
        viewModelState = value
    }
}
