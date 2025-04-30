package com.degrin.bitcoinwallet.feature.transactions.domain.impl

import com.degrin.bitcoinwallet.BuildConfig
import com.degrin.bitcoinwallet.core.network.api.EsploraApi
import com.degrin.bitcoinwallet.core.network.model.TransactionDto
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionType
import com.degrin.bitcoinwallet.feature.transactions.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val api: EsploraApi
) : TransactionRepository {

    override suspend fun getAddressTransactions(): List<TransactionSortedData> {
        return api.getAddressUtxo(BuildConfig.WALLET_ADDRESS).map { transactionDto ->
            processTransaction(transactionDto)
        }
    }

    private fun processTransaction(transaction: TransactionDto): TransactionSortedData {
        val myAddress = BuildConfig.WALLET_ADDRESS

        val isOutgoing = transaction.vIn.any { input ->
            input.prevOut.scriptPublicKeyAddress == myAddress
        }

        val isIncoming = transaction.vOut.any { out ->
            out.scriptPublicKeyAddress == myAddress
        }

        val type: TransactionType = when {
            isOutgoing && transaction.vOut.any { it.scriptPublicKeyAddress != myAddress } -> TransactionType.OUTGOING
            isIncoming -> TransactionType.INCOMING
            isOutgoing && !isIncoming -> TransactionType.INTERNAL
            else -> TransactionType.UNKNOWN
        }

        val amountBtc = when (type) {
            TransactionType.OUTGOING -> transaction.vOut
                .filter { it.scriptPublicKeyAddress != myAddress }
                .sumOf { it.value }.toDouble() / VALUE_PER_BTC

            TransactionType.INCOMING -> transaction.vOut
                .filter { it.scriptPublicKeyAddress == myAddress }
                .sumOf { it.value }.toDouble() / VALUE_PER_BTC

            TransactionType.INTERNAL -> NULLABLE_VALUE
            TransactionType.UNKNOWN -> NULLABLE_VALUE
        }

        return TransactionSortedData(
            type = type,
            amount = amountBtc
        )
    }

    companion object{
        private const val VALUE_PER_BTC = 100_000_000.0
        private const val NULLABLE_VALUE = 100_000_000.0
    }
}
