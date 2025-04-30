package com.degrin.bitcoinwallet.feature.transactions.domain.useCase

import com.degrin.bitcoinwallet.feature.transactions.domain.repository.TransactionRepository
import com.degrin.bitcoinwallet.feature.wallet.domain.repository.WalletRepository

class TransactionUseCase(
    private val repository: TransactionRepository,
    private val walletRepository: WalletRepository
) {
    suspend fun getBalance() = kotlin.runCatching {
        walletRepository.getBalance()
    }

    suspend fun getAddressTransactions() = kotlin.runCatching {
        repository.getAddressTransactions()
    }
}
