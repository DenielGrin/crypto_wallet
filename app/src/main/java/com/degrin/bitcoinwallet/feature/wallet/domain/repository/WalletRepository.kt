package com.degrin.bitcoinwallet.feature.wallet.domain.repository

import java.math.BigDecimal

interface WalletRepository {
    suspend fun getBalance(): BigDecimal
}
