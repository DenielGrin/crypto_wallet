package com.degrin.bitcoinwallet.feature.wallet.data.model

data class TransactionParams(
    val privateKey: String,
    val destinationAddress: String,
    val amount: Long,
    val feeAmount: Long,
    val utxo: Utxo
)