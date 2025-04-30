package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionInput(
    val txid: String? = null,
    val vout: Int? = null,
    val inputScript: String? = null,
    val witness: List<String> = emptyList(),
    val sequence: Long
)
