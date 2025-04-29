package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.degrin.bitcoinwallet.feature.wallet.domain.useCase.WalletUseCase

class WalletViewModel(
    private val walletUseCase: WalletUseCase
) : ViewModel() {

    var viewModelState by mutableStateOf<WalletScreenState>(WalletScreenState.None)
        private set

    init {
        updateState(WalletScreenState.Loading)
        getData()
    }

    private fun getData() {
        updateState(WalletScreenState.Loading)
    }

    private fun updateState(value: WalletScreenState) {
        viewModelState = value
    }

    fun reloadData() {
        getData()
    }
}
