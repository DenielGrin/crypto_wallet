package com.degrin.bitcoinwallet.feature.wallet.domain.repository

import com.degrin.bitcoinwallet.core.network.model.TransactionDto
import java.math.BigDecimal

interface WalletRepository {

    suspend fun getBalance(): BigDecimal

    suspend fun getUtxosForAddress(hardcodedSenderAddress: String):List<TransactionDto>

    suspend fun sendTransaction(transactionHex: String): String

}
