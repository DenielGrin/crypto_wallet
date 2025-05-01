package com.degrin.bitcoinwallet.feature.transactions.presentation.screen

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName
import com.degrin.bitcoinwallet.core.navigation.utils.navController.navigateToTab
import com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel.TransactionsViewModel
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.WalletScreen
import java.math.BigDecimal
import org.koin.androidx.compose.koinViewModel

object TransactionsScreen : Screen {

    override val screenName: String = defaultScreenName()

    @Composable
    override fun Content(navController: NavController, args: Bundle?) {
        val viewModel: TransactionsViewModel = koinViewModel()
        val balance: BigDecimal? by viewModel.balance.collectAsState()

        TransactionsContent(
            state = viewModel.viewModelState,
            balance = balance,
            onSendClick = {
                navController.navigateToTab(WalletScreen.defaultScreenName())
            },
            onReloadClick = viewModel::reloadData
        )
    }
}
