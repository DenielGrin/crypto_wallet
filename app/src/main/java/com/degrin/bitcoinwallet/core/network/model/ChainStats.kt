package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class ChainStats (
    @SerializedName("funded_txo_sum")
    val fundedSum: Long,
    @SerializedName("spent_txo_sum")
    val spentSum: Long
)
