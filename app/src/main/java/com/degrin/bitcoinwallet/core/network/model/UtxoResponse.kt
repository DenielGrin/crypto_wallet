package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UtxoResponse(
    val txid: String,
    val vout: Int,
    val status: UtxoStatus,
    val value: Long
)
