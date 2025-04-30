package com.degrin.bitcoinwallet.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class FeeEstimates(
    val fastestFee: Int,
    val halfHourFee: Int,
    val hourFee: Int,
    val minimumFee: Int,
    val conservativeFee: Int? = null,
    val middlingFee: Int? = null,
    val economyFee: Int? = null
)
