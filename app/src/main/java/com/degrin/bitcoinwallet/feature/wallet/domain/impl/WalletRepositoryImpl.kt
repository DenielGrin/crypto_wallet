package com.degrin.bitcoinwallet.feature.wallet.domain.impl

import com.degrin.bitcoinwallet.BuildConfig
import com.degrin.bitcoinwallet.core.network.api.EsploraApi
import com.degrin.bitcoinwallet.core.network.model.TransactionDto
import com.degrin.bitcoinwallet.feature.wallet.domain.repository.WalletRepository
import java.math.BigDecimal

class WalletRepositoryImpl(
    private val api: EsploraApi
) : WalletRepository {

    override suspend fun getBalance(): BigDecimal {
        val response = api.getAddressInfo(BuildConfig.WALLET_ADDRESS)
        val fundedSum = BigDecimal(response.chainStats.fundedSum)
        val spentSum = BigDecimal(response.chainStats.spentSum)
        return fundedSum.minus(spentSum)
    }

    override suspend fun getUtxosForAddress(): List<TransactionDto> {
        return api.getAddressUtxo(SENDER_ADDRESS)
    }

    override suspend fun sendTransaction(transactionHex: String): String {
        return api.sendTransaction(transactionHex)
    }

    companion object {
        private const val WALLET_ADDRESS = "tb1qgy9qzjrferq7ghyclyjeqvlky7qgh2a8pew4we"
        private const val SENDER_ADDRESS = "tb1qgy9qzjrferq7ghyclyjeqvlky7qgh2a8pew4we"
        private const val HARDCODED_PRIVATE_KEY = "p2wpkh:cRzQyWZEmYJ8gq7K8nKmhKQxKcnpv5pNBi37tEtU2vmmqgCXgyp5"
    }
}
