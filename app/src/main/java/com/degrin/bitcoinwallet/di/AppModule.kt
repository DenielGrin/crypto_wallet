package com.degrin.bitcoinwallet.di

import com.degrin.bitcoinwallet.core.network.api.EsploraApi
import com.degrin.bitcoinwallet.core.network.provider.RetrofitProvider
import com.degrin.bitcoinwallet.feature.transactions.di.transactionModule
import com.degrin.bitcoinwallet.feature.wallet.di.walletModule
import org.koin.core.module.Module
import org.koin.dsl.module

fun getKoinModules(): List<Module> {
    return networkModule + featureModules
}

val featureModules = listOf(
    walletModule,
    transactionModule
)

val networkModule = module {
    single<EsploraApi> { RetrofitProvider.esploraApi }
}