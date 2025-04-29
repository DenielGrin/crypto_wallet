package com.degrin.bitcoinwallet.core.network.api

import com.degrin.bitcoinwallet.core.network.model.BlockResponse
import com.degrin.bitcoinwallet.core.network.model.BlockStatusResponse
import com.degrin.bitcoinwallet.core.network.model.FeeEstimates
import com.degrin.bitcoinwallet.core.network.model.TransactionResponse
import com.degrin.bitcoinwallet.core.network.model.TransactionStatusResponse
import com.degrin.bitcoinwallet.core.network.model.UtxoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface EsploraApi {

     @GET("address/{address}/utxo")
     suspend fun getAddressUtxo(@Path("address") address: String): List<UtxoResponse>

     @GET("tx/{txid}")
     suspend fun getTransaction(@Path("txid") txid: String): TransactionResponse

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
