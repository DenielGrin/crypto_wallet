package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BlockStatusResponse(
    val isBestChain: Boolean,
    val height: Int?,
    val nextBestHash: String?
)
