package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state

data class InputFieldsState(
    val address: String = "",
    val amount: String = "",
    val amountError: Int? = null,
    val addressError: Int? = null,
)
