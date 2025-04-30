package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state

data class InputFieldsState(
    val address: String = "tb1ql2xhwrpxakcganf3t420tm24xe6n6j89xvxz85",
    val amount: String = "0.00000544",
    val amountError: Int? = null,
    val addressError: Int? = null,
)
