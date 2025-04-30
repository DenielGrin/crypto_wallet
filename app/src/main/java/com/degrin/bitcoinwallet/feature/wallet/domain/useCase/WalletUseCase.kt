package com.degrin.bitcoinwallet.feature.wallet.domain.useCase

import com.degrin.bitcoinwallet.feature.wallet.domain.repository.WalletRepository

class WalletUseCase(
    private val repository: WalletRepository
) {
    suspend fun getBalance() = kotlin.runCatching {
        repository.getBalance()
    }

    suspend fun getUtxosForAddress(hardcodedSenderAddress: String) = kotlin.runCatching {
        repository.getUtxosForAddress(hardcodedSenderAddress)
    }

    suspend fun sendTransaction(transactionHex: String) = kotlin.runCatching {
        repository.sendTransaction(transactionHex)
    }
}
