package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class TransactionDto(
    @SerializedName("txid")
    val txId: String,
    @SerializedName("vout")
    val vOut: List<Out>,
    @SerializedName("fee")
    val fee: Long,
    @SerializedName("status")
    val status: Status
)
