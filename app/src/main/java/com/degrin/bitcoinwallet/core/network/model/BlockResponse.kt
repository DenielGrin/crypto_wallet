package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BlockResponse(
    val id: String,
    val height: Int,
    val version: Int,
    val timestamp: Long,
    val txCount: Int,
    val size: Int,
    val weight: Int,
    val merkleRoot: String,
    val previousBlockHash: String?,
    val medianTimestamp: Long,
    val nonce: Long,
    val bits: Long,
    val difficulty: Long,
    val blockChainWork: String
)
