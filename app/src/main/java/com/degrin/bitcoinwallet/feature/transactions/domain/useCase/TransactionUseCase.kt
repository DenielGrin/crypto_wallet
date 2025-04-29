package com.degrin.bitcoinwallet.feature.transactions.domain.useCase

import com.degrin.bitcoinwallet.feature.transactions.domain.repository.TransactionRepository

class TransactionUseCase(
    private val repository: TransactionRepository
) {

}
