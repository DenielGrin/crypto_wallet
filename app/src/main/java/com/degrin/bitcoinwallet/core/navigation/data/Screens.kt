package com.degrin.bitcoinwallet.core.navigation.data

import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionsScreen
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.WalletScreen

object Screens {

    private val walletScreens: List<Screen> = listOf(
        WalletScreen,
    )

    private val transactionsScreens: List<Screen> = listOf(
        TransactionsScreen
    )

    fun getAllScreens(): List<Screen> = listOf(
        walletScreens,
        transactionsScreens,
    ).flatten()

}
