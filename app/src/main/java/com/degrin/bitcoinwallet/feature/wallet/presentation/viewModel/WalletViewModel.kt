package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.network.model.TransactionDTO
import com.degrin.bitcoinwallet.core.network.model.Utxo
import com.degrin.bitcoinwallet.feature.wallet.data.model.TransactionParams
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

            amount.toDoubleOrNull() == null || amount.toBigDecimal() <= BigDecimal.ZERO -> {
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

        val amount = (_inputState.value.amount.toBigDecimal() * BigDecimal.valueOf(100_000_000)).toLong()
        val address = _inputState.value.address

        viewModelScope.launch {
            sendTransactionInternal(address, amount)
        }
    }

    private suspend fun sendTransactionInternal(address: String, amount: Long) {
        try {
            updateState(WalletScreenState.Loading)

            val utxosResult = walletUseCase.getUtxosForAddress(HARDCODED_SENDER_ADDRESS)
            utxosResult.onSuccess { utxos ->
                processUtxos(utxos, address, amount, utxos)
            }.onFailure { exception ->
                updateAction(Actions.ErrorSendingCoins(exception.message))
            }

        } catch (e: Exception) {
            updateAction(Actions.ErrorSendingCoins(e.message))
        } finally {
            getData()
        }
    }

    private suspend fun processUtxos(
        address: String,
        amount: Long,
        utxos: List<TransactionDTO>
    ) {
        if (utxos.isEmpty()) {
            updateAction(Actions.ErrorSendingCoins("No UTXOs available"))
            return
        }

        val utxo = findSuitableUtxo(utxos, amount, FEE_AMOUNT, DUST_THRESHOLD)
        if (utxo == null) {
            updateAction(Actions.ErrorSendingCoins("Insufficient funds"))
            return
        }

        buildAndSendTransaction(address, amount, utxo)
    }

    private suspend fun buildAndSendTransaction(
        address: String,
        amount: Long,
        utxo: Utxo
    ) {
        val params = TransactionParams(
            privateKey = HARDCODED_PRIVATE_KEY,
            destinationAddress = address,
            amount = amount,
            feeAmount = FEE_AMOUNT,
            utxo = utxo
        )

        val transactionHexResult = walletTransactionBuilder.buildTransaction(params)

        transactionHexResult.onSuccess { transactionHex ->
            val txidResult = walletUseCase.sendTransaction(transactionHex)
            txidResult.onSuccess {
                updateAction(Actions.SuccessSendingCoins)
            }.onFailure { exception ->
                updateAction(Actions.ErrorSendingCoins(exception.message))
            }
        }.onFailure { exception ->
            updateAction(Actions.ErrorSendingCoins(exception.message))
        }
    }

    private fun findSuitableUtxo(
        transactions: List<TransactionDTO>,
        amount: Long,
        feeAmount: Long,
        dustThreshold: Long
    ): Utxo? {
        return transactions.firstOrNull { tx ->
            tx.status.confirmed && tx.vOut.any { vout ->
                vout.value >= (amount + feeAmount + dustThreshold) &&
                    !transactions.any { otherTx ->
                        otherTx.vIn.any { vin ->
                            vin.txId == tx.txId &&
                                vin.vOut.toLong() == tx.vOut.indexOf(vout).toLong()
                        }
                    }
            }
        }?.let { tx ->
            val voutIndex = tx.vOut.indexOfFirst { it.value >= (amount + feeAmount + dustThreshold) }.toLong()
            Utxo(tx.txId, voutIndex, tx.vOut[voutIndex.toInt()].value)
        }
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
        data object SuccessSendingCoins : Actions()
        data class ErrorSendingCoins(val message: String?) : Actions()
    }
}
