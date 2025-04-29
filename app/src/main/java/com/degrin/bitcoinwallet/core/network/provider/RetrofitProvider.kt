package com.degrin.bitcoinwallet.core.network.provider

import com.degrin.bitcoinwallet.core.network.api.EsploraApi
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitProvider {

     private const val BASE_URL = "https://mempool.space/signet/api/"

     private val contentType = "application/json".toMediaType()

     private val json = Json {
         ignoreUnknownKeys = true
         coerceInputValues = true
     }

     private val okHttpClient: OkHttpClient by lazy {
         val httpLoggingInterceptor = HttpLoggingInterceptor()
         httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

         OkHttpClient.Builder()
             .addInterceptor(httpLoggingInterceptor)
             .build()
     }

     val retrofit: Retrofit by lazy {
         Retrofit.Builder()
             .baseUrl(BASE_URL)
             .client(okHttpClient)
             .addConverterFactory(json.asConverterFactory(contentType))
             .build()
     }

     val esploraApi: EsploraApi by lazy {
         retrofit.create(EsploraApi::class.java)
     }
}