package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class TransactionDTO(
    @SerializedName("txid")
    val txId: String,
    @SerializedName("vin")
    val vIn: List<In>,
    @SerializedName("vout")
    val vOut: List<Out>,
    val fee: Long,
    val status: Status
)
