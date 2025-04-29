package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class In(
    @SerializedName("txid")
    val txId: String,
    @SerializedName("vout")
    val vOut: Int,
    @SerializedName("prevout")
    val prevOut: PrevOut
)
