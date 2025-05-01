package com.degrin.bitcoinwallet.core.network.api

import com.degrin.bitcoinwallet.core.network.model.AddressDto
import com.degrin.bitcoinwallet.core.network.model.TransactionDto
import com.degrin.bitcoinwallet.core.network.model.TransactionStatusResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EsploraApi {

    @GET("address/{address}")
    suspend fun getAddressInfo(@Path("address") address: String): AddressDto

    @GET("address/{address}/txs")
    suspend fun getAddressUtxo(@Path("address") address: String): List<TransactionDto>

    @POST("tx")
    suspend fun sendTransaction(@Body rawTx: String): String

}
