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
        return api.getAddressUtxo(BuildConfig.WALLET_ADDRESS)
    }

    override suspend fun sendTransaction(transactionHex: String): String {
        return api.sendTransaction(transactionHex)
    }

}
