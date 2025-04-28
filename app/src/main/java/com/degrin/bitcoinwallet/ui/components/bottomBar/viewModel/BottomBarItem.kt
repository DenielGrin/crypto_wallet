@file:Suppress("Filename", "MatchingDeclarationName")

package com.degrin.bitcoinwallet.ui.components.bottomBar.viewModel

import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.TransactionScreen

sealed class BottomBarItem(
    val screenName: String?,
    val labelId: Int,
    val iconId: Int
) {
    data object TransactionsTab : BottomBarItem(
        screenName = TransactionScreen.defaultScreenName(),
        labelId = R.string.menu_transaction_label,
        iconId = R.drawable.ic_shield
    )

    data object DAppsTab : BottomBarItem(
        screenName = null,
        labelId = R.string.menu_dapps_label,
        iconId = R.drawable.ic_apps
    )

    data object SwapTab : BottomBarItem(
        screenName = null,
        labelId = R.string.menu_swap_label,
        iconId = R.drawable.ic_swap
    )

    data object SettingsTab : BottomBarItem(
        screenName = null,
        labelId = R.string.menu_settings_label,
        iconId = R.drawable.ic_settings
    )

}
