package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val txid: String,
    val version: Int,
    val timeLock: Int,
    val size: Int,
    val weight: Int,
    val fee: Long,
    val status: TransactionStatus,
    val vin: List<TransactionInput>,
    val vout: List<TransactionOutput>
)
