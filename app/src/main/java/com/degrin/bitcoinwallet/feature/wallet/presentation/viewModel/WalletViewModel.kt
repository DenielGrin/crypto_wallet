package com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.network.model.TransactionDto
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

        val amount =
            (_inputState.value.amount.toBigDecimal() * BigDecimal.valueOf(100_000_000)).toLong()
        val address = _inputState.value.address

        viewModelScope.launch {
            sendTransactionInternal(address, amount)
        }
    }

    private suspend fun sendTransactionInternal(address: String, amount: Long) {
        updateState(WalletScreenState.Loading)

        walletUseCase.getUtxosForAddress(HARDCODED_SENDER_ADDRESS)
            .onSuccess { transactions ->
                val utxo = findSuitableUtxo(transactions = transactions, amount = amount)
                if (utxo != null) {
                    buildAndSendTransaction(
                        address = address,
                        amount = amount,
                        utxo = utxo
                    )
                } else {
                    updateAction(Actions.ErrorSendingCoins("Insufficient funds or no suitable UTXO"))
                    updateState(WalletScreenState.Error("Insufficient funds or no suitable UTXO"))
                }
            }
            .onFailure { exception ->
                updateAction(Actions.ErrorSendingCoins(exception.message))
                updateState(WalletScreenState.Error(exception.message))
            }
//        finally {
//            getData()
//        }
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

        walletTransactionBuilder.buildTransaction(params)
            .onSuccess { transactionHex ->
                walletUseCase.sendTransaction(transactionHex)
                    .onSuccess {
                        updateAction(Actions.SuccessSendingCoins(id = it))
                    }
                    .onFailure { exception ->
                        updateAction(Actions.ErrorSendingCoins(exception.message))
                    }
            }
            .onFailure { exception ->
                updateAction(Actions.ErrorSendingCoins(exception.message))
            }
    }

    private fun findSuitableUtxo(
        transactions: List<TransactionDto>,
        amount: Long,
        feeAmount: Long = FEE_AMOUNT,
        dustThreshold: Long = DUST_THRESHOLD
    ): Utxo? {
        for (tx in transactions) {
            if (tx.status.confirmed) {
                tx.vOut.forEachIndexed { index, out ->
                    if (out.value >= (amount + feeAmount + dustThreshold)) {
                        return Utxo(
                            txId = tx.txId,
                            vOutIndex = index.toLong(),
                            status = tx.status,
                            value = out.value,
                        )
                    }
                }
            }
        }
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
        private const val HARDCODED_PRIVATE_KEY =
            "p2wpkh:cVzN65ufaKTss5Shu55QoUZaFtrwSjmcTMhVyEEjbtveBUwuhBeT"
        private const val HARDCODED_SENDER_ADDRESS = "tb1q3ae59urk6fjh6seectaqe3lavh5dx84penznwq"
        private const val FEE_AMOUNT = 250L
        private const val DUST_THRESHOLD = 250L
    }
}
