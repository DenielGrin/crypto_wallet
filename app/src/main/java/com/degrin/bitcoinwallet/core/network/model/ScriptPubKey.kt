package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ScriptPubKey(
    val asm: String,
    val hex: String,
    val type: String,
    val address: String? = null
)
