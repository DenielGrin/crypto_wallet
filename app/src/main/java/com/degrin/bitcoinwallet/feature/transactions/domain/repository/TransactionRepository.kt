package com.degrin.bitcoinwallet.feature.transactions.domain.repository

import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData

interface TransactionRepository {

    suspend fun getAddressTransactions(): List<TransactionSortedData>

}
