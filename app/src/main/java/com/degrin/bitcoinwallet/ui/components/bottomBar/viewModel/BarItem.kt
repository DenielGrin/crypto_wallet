@file:Suppress("Filename", "MatchingDeclarationName")

package com.degrin.bitcoinwallet.ui.components.bottomBar.viewModel

import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionsScreen
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.WalletScreen

sealed class BarItem(
    val screenName: String?,
    val labelId: Int,
    val iconId: Int
) {
    data object WalletTab : BarItem(
        screenName = WalletScreen.defaultScreenName(),
        labelId = R.string.menu_wallet_label,
        iconId = R.drawable.ic_shield
    )

    data object TransactionsTab : BarItem(
        screenName = TransactionsScreen.defaultScreenName(),
        labelId = R.string.menu_transaction_label,
        iconId = R.drawable.ic_swap
    )

    data object DAppsTab : BarItem(
        screenName = null,
        labelId = R.string.menu_dapps_label,
        iconId = R.drawable.ic_apps
    )

    data object SettingsTab : BarItem(
        screenName = null,
        labelId = R.string.menu_settings_label,
        iconId = R.drawable.ic_settings
    )

}
