package com.degrin.bitcoinwallet.di

import com.degrin.bitcoinwallet.feature.transactionDetails.di.transactionDetailsModule
import com.degrin.bitcoinwallet.feature.wallet.di.walletModule
import org.koin.core.module.Module

fun getKoinModules(): List<Module> {
    return featureModules
}

val featureModules = listOf(
    walletModule,
    transactionDetailsModule
)
