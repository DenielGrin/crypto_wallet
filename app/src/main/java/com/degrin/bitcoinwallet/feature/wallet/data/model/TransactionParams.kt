package com.degrin.bitcoinwallet.feature.wallet.data.model

import com.degrin.bitcoinwallet.core.network.model.Utxo

data class TransactionParams(
    val privateKey: String,
    val destinationAddress: String,
    val amount: Long,
    val feeAmount: Long,
    val utxo: Utxo
)
