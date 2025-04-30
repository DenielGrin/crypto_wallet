package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state

data class InputFieldsState(
    val amount: String = "",
    val address: String = "",
    val amountError: Int? = null,
    val addressError: Int? = null,
)
