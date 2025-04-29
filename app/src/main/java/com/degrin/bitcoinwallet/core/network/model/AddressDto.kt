package com.degrin.bitcoinwallet.core.network.model

import com.google.gson.annotations.SerializedName

data class AddressDto (
    @SerializedName("chain_stats")
    val chainStats: ChainStats
)
