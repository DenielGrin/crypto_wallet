package com.degrin.bitcoinwallet.feature.wallet.domain.impl

import com.degrin.bitcoinwallet.core.network.api.EsploraApi
import com.degrin.bitcoinwallet.feature.wallet.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val api: EsploraApi
) : WalletRepository {

    override suspend fun getBalance(): Long {
        val response = api.getAddressInfo(WALLET_ADDRESS)
        return response.chainStats.fundedSum - response.chainStats.spentSum
    }

    companion object{
        private const val WALLET_ADDRESS = "tb1q3ae59urk6fjh6seectaqe3lavh5dx84penznwq"
    }
}
