package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class PrevOut(
    val value: Long,
    @SerializedName("scriptpubkey_address")
    val scriptPublicKeyAddress: String
)
