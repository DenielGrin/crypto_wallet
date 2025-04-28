package com.degrin.bitcoinwallet.core.navigation.data

import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.feature.transactionDetails.presentation.screen.TransactionDetailsScreen
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.TransactionScreen

object Screens {

    private val transactionsScreens: List<Screen> = listOf(
        TransactionScreen,
        TransactionDetailsScreen
    )

    fun getAllScreens(): List<Screen> = listOf(
        transactionsScreens,
    ).flatten()

}
