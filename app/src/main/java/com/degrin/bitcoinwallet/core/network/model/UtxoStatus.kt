package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UtxoStatus(
    val confirmed: Boolean,
    val blockHeight: Int? = null,
    val blockHash: String? = null,
    val blockTime: Long? = null
)
