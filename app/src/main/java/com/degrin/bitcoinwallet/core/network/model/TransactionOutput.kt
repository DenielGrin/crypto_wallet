package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionOutput(
    val value: Long,
    val n: Int,
    val scriptPubKey: ScriptPubKey
)
