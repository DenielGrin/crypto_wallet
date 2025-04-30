package com.degrin.bitcoinwallet.core.network.model

data class Status(
    val blockHeight: Long,
    val blockHash: String,
    val confirmed: Boolean
)
