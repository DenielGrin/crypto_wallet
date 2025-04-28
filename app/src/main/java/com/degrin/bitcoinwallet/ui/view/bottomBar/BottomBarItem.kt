@file:Suppress("Filename", "MatchingDeclarationName")

package com.degrin.bitcoinwallet.ui.view.bottomBar

import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.navigation.utils.defaultScreenName
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionScreen

sealed class BottomBarItem(
    val screenName: String,
    val labelId: Int,
    val iconId: Int
) {
    data object TransactionsTab : BottomBarItem(
        screenName = TransactionScreen.defaultScreenName(),
        labelId = R.string.menu_transaction_label,
        iconId = R.drawable.ic_wallet
    )

    object DAppsTab : BottomBarItem(
        screenName = "DAppsTab",
        labelId = R.string.menu_dapps_label,
        iconId = R.drawable.ic_apps
    )

    object SwapTab : BottomBarItem(
        screenName = "SwapTab",
        labelId = R.string.menu_swap_label,
        iconId = R.drawable.ic_swap
    )

    object SettingsTab : BottomBarItem(
        screenName = "SettingsTab",
        labelId = R.string.menu_settings_label,
        iconId = R.drawable.ic_settings
    )

}
