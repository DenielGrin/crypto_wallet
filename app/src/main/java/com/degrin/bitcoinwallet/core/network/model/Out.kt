package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class Out(
    val value: Long,
    @SerializedName("scriptpubkey")
    val scriptPublicKey: String,
    @SerializedName("scriptpubkey_address")
    val scriptPublicKeyAddress: String
)
