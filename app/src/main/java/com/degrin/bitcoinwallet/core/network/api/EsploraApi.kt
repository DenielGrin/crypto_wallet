package com.degrin.bitcoinwallet.core.network.api

import com.degrin.bitcoinwallet.core.network.model.AddressDto
import com.degrin.bitcoinwallet.core.network.model.BlockResponse
import com.degrin.bitcoinwallet.core.network.model.BlockStatusResponse
import com.degrin.bitcoinwallet.core.network.model.FeeEstimates
import com.degrin.bitcoinwallet.core.network.model.TransactionDTO
import com.degrin.bitcoinwallet.core.network.model.TransactionStatusResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface EsploraApi {

    @GET("address/{address}")
    suspend fun getAddressInfo(@Path("address") address: String): AddressDto

    @GET("address/{address}/txs")
    suspend fun getTransactions(@Path("address") address: String): List<TransactionDTO>

    @GET("tx/{txid}/status")
    suspend fun getTransactionStatus(@Path("txid") txid: String): TransactionStatusResponse

    @GET("blocks/{hash}")
    suspend fun getBlock(@Path("hash") hash: String): BlockResponse

    @GET("blocks/{hash}/status")
    suspend fun getBlockStatus(@Path("hash") hash: String): BlockStatusResponse

    @GET("blocks/tip/hash")
    suspend fun getTipHash(): String

    @GET("blocks/tip/height")
    suspend fun getTipHeight(): Int

    @GET("fee-estimates")
    suspend fun getFeeEstimates(): FeeEstimates

    @GET("tx")
    suspend fun broadcastTransaction(@Body rawTx: String): String
}
