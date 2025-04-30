package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degrin.bitcoinwallet.BuildConfig
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.network.model.TransactionDto
import com.degrin.bitcoinwallet.core.network.model.Utxo
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionParams
import com.degrin.bitcoinwallet.feature.wallet.domain.impl.WalletTransactionBuilder
import com.degrin.bitcoinwallet.feature.wallet.domain.useCase.WalletUseCase
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.InputFieldsState
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.WalletScreenState
import java.math.BigDecimal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletUseCase: WalletUseCase,
    private val walletTransactionBuilder: WalletTransactionBuilder,
) : ViewModel() {

    var viewModelState by mutableStateOf<WalletScreenState>(WalletScreenState.None)
        private set
    private val _actions: Channel<Actions> = Channel(Channel.BUFFERED)
    val actions: Flow<Actions> = _actions.receiveAsFlow()

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
                    Log.d(TAG, "getBalance successful: $balance")
                    updateState(value = WalletScreenState.Data(balance = balance))
                }
                .onFailure { exception ->
                    Log.e(TAG, "getBalance failed: ${exception.message}")
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
            amount.isBlank() -> amountError = R.string.error_invalid_amount_empty
            amount.toDoubleOrNull() == null || amount.toBigDecimal() <= BigDecimal.ZERO -> amountError =
                R.string.error_invalid_amount_format
        }

        when {
            address.isBlank() -> addressError = R.string.error_invalid_address_empty
            !isValidBitcoinAddress(address) -> addressError = R.string.error_invalid_address_format
        }

        _inputState.value =
            _inputState.value.copy(amountError = amountError, addressError = addressError)
        return amountError == null && addressError == null
    }

    fun sendTransaction() {
        if (!validateInput()) return

        val amount =
            (_inputState.value.amount.toBigDecimal() * BigDecimal.valueOf(100_000_000)).toLong()
        val address = _inputState.value.address

        sendTransactionInternal(address = address, amount = amount)
    }

    private fun sendTransactionInternal(address: String, amount: Long) {
        viewModelScope.launch {
            Log.d(TAG, "sendTransactionInternal called for address: $address, amount: $amount")

            walletUseCase.getUtxosForAddress()
                .onSuccess { transactions ->
                    Log.d(TAG, "getUtxosForAddress successful, found ${transactions.size} UTXOs")
                    val utxo = findSuitableUtxo(transactions = transactions, amount = amount)
                    if (utxo != null) {
                        buildAndSendTransaction(address = address, amount = amount, utxo = utxo)
                    } else {
                        Log.w(TAG, "findSuitableUtxo failed to find a suitable UTXO")
                        updateAction(Actions.ErrorSendingCoins("Insufficient funds or no suitable UTXO"))
                    }
                }
                .onFailure { exception ->
                    Log.e(TAG, "getUtxosForAddress failed: ${exception.message}")
                    updateAction(Actions.ErrorSendingCoins(exception.message))
                }
        }
    }

    private suspend fun buildAndSendTransaction(
        address: String,
        amount: Long,
        utxo: Utxo
    ) {
        val params = TransactionParams(
            privateKey = BuildConfig.WALLET_KEY,
            destinationAddress = address,
            amount = amount,
            feeAmount = FEE_AMOUNT,
            utxo = utxo
        )

        walletTransactionBuilder.buildTransaction(params)
            .onSuccess { transactionHex ->
                Log.d(TAG, "buildTransaction successful, raw transaction hex: $transactionHex")
                walletUseCase.sendTransaction(transactionHex)
                    .onSuccess { txid ->
                        Log.d(TAG, "sendTransaction successful, TXID: $txid")
                        updateAction(Actions.SuccessSendingCoins(id = txid))
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "sendTransaction failed: ${exception.message}")
                        updateAction(Actions.ErrorSendingCoins(exception.message))
                    }
            }
            .onFailure { exception ->
                Log.e(TAG, "buildTransaction failed: ${exception.message}")
                updateAction(Actions.ErrorSendingCoins(exception.message))
            }
    }

    private fun findSuitableUtxo(
        transactions: List<TransactionDto>,
        amount: Long
    ): Utxo? {
        Log.d(TAG, "findSuitableUtxo called with amount: $amount")
        for (tx in transactions) {
            if (tx.status.confirmed) {
                tx.vOut.forEachIndexed { index, out ->
                    if (out.value >= (amount + FEE_AMOUNT + DUST_THRESHOLD)) {
                        // Проверить, что этот выход еще не был использован в качестве входа (UTXO)
                        val isUsed = transactions.any { transaction ->
                            transaction.vIn.any { vin -> vin.txId == tx.txId && vin.vOut == index }
                        }

                        // Если UTXO не был использован, вернуть его
                        if (!isUsed) {
                            Log.d(
                                TAG,
                                "Found suitable UTXO: txId=${tx.txId}, vOutIndex=$index, value=${out.value}"
                            )
                            return Utxo(
                                txId = tx.txId,
                                vOutIndex = index.toLong(),
                                value = out.value,
//                                status = tx.status
                            )
                        } else {
                            Log.w(
                                TAG,
                                "UTXO is used: txId=${tx.txId}, vOutIndex=$index, value=${out.value}"
                            )
                        }
                    } else {
                        Log.w(
                            TAG,
                            "UTXO value is too low: txId=${tx.txId}, vOutIndex=$index, value=${out.value}, required=${amount + FEE_AMOUNT + DUST_THRESHOLD}"
                        )
                    }
                }
            }
        }
        Log.w(TAG, "findSuitableUtxo: No suitable UTXO found.")
        return null
    }

    private fun isValidBitcoinAddress(address: String): Boolean {
        // TODO Find a better way to validate Bitcoin address
        return address.startsWith("tb1")
    }

    fun reloadData() {
        getData()
    }

    private fun updateAction(actions: Actions) {
        _actions.trySend(actions)
    }

    sealed class Actions {
        data class SuccessSendingCoins(val id: String) : Actions()
        data class ErrorSendingCoins(val message: String?) : Actions()
    }

    companion object {
        private const val FEE_AMOUNT = 250L
        private const val DUST_THRESHOLD = 250L
        private const val TAG = "WalletViewModel"
    }
}
