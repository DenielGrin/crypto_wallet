package com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degrin.bitcoinwallet.feature.transactions.domain.useCase.TransactionUseCase
import java.math.BigDecimal
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val useCase: TransactionUseCase
) : ViewModel() {

    var viewModelState by mutableStateOf<TransactionScreenState>(TransactionScreenState.None)
        private set

    private val _balance: MutableStateFlow<BigDecimal?> = MutableStateFlow(null)
    val balance: StateFlow<BigDecimal?> = _balance.asStateFlow()

    init {
        updateState(TransactionScreenState.Loading)
        viewModelScope.launch {
            listOf(
                async { getBalanceData() },
                async { getData() }
            ).awaitAll()
        }
    }

    private fun getBalanceData() {

        viewModelScope.launch {
            useCase.getBalance()
                .onSuccess { balance ->
                    _balance.value = balance
                }
                .onFailure { exception ->
                    Log.e(
                        TAG,
                        "Error caught in getBalanceData failed: ${exception.message}"
                    )
                }
        }
    }

    private fun getData() {
        updateState(TransactionScreenState.Loading)

        viewModelScope.launch {
            useCase.getAddressTransactions()
                .onSuccess { data ->
                    updateState(
                        value = when {
                            data.isEmpty() -> TransactionScreenState.NullableData
                            else -> TransactionScreenState.Data(data)
                        }
                    )
                }
        }
    }

    private fun updateState(value: TransactionScreenState) {
        viewModelState = value
    }

    fun reloadData() {
        getData()
    }

    companion object{
        private const val TAG = "TransactionsViewModel"
    }
}
