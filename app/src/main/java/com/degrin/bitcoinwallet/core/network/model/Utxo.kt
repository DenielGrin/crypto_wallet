package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class Utxo(
    @SerializedName("txid")
    val txId: String,
    @SerializedName("vout")//voutIndex
    val vOutIndex: Long,
    @SerializedName("status")
    val status: Status,
    val value: Long
)
