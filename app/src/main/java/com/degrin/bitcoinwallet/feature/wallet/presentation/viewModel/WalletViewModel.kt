package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.feature.wallet.domain.useCase.WalletUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletUseCase: WalletUseCase
) : ViewModel() {

    var viewModelState by mutableStateOf<WalletScreenState>(WalletScreenState.None)
        private set

    private val _inputState = MutableStateFlow(InputFieldsState())
    val inputState: StateFlow<InputFieldsState> = _inputState

    init {
        getData()
    }

    private fun getData() {
        updateState(value = WalletScreenState.Loading)

        viewModelScope.launch {
            walletUseCase.getBalance()
                .onSuccess { balance ->
                    updateState(value = WalletScreenState.Data(balance = balance))
                }
                .onFailure { exception ->
                    updateState(value = WalletScreenState.Error(exception.message))
                }
        }
    }

    private fun updateState(value: WalletScreenState) {
        viewModelState = value
    }

    fun onAmountChanged(amount: String) {
        _inputState.value = _inputState.value.copy(amount = amount)
        validateInput()
    }

    fun onAddressChanged(address: String) {
        _inputState.value = _inputState.value.copy(address = address)
        validateInput()
    }

    private fun validateInput(): Boolean {
        var amountError: Int? = null
        var addressError: Int? = null

        val amount = _inputState.value.amount
        val address = _inputState.value.address

        when {
            amount.isBlank() -> {
                amountError = R.string.error_invalid_amount_empty
            }

            amount.toDoubleOrNull() == null -> {
                amountError = R.string.error_invalid_amount_format
            }
        }

        when {
            address.isBlank() -> {
                addressError = R.string.error_invalid_address_empty
            }

            !isValidBitcoinAddress(address) -> {
                addressError = R.string.error_invalid_address_format
            }
        }

        _inputState.value = _inputState.value.copy(
            amountError = amountError,
            addressError = addressError
        )

        return amountError == null && addressError == null
    }

    fun sendTransaction() {
        if (!validateInput()) {
            return
        }

        val amount = _inputState.value.amount.toDouble()
        val address = _inputState.value.address

//        viewModelScope.launch {
//            walletUseCase.sendTransaction(address, amount)
//                .onSuccess {
//                    updateState(value = WalletScreenState.Success)
//                }
//                .onFailure { exception ->
//                    updateState(value = WalletScreenState.Error(exception.message))
//                }
//        }
    }

    private fun isValidBitcoinAddress(address: String): Boolean {
        // TODO Find a better way to validate Bitcoin address
        return address.startsWith("tb1")
    }

    fun reloadData() {
        getData()
    }
}
