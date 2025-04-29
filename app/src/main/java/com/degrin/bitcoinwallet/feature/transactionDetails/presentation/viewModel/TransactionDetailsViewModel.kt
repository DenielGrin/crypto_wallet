package com.degrin.bitcoinwallet.feature.transactionDetails.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.degrin.bitcoinwallet.feature.transactionDetails.domain.useCase.TransactionDetailsUseCase

class TransactionDetailsViewModel(
    private val useCase: TransactionDetailsUseCase
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
