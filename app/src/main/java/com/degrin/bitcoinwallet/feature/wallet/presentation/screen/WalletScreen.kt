package com.degrin.bitcoinwallet.feature.wallet.presentation.screen

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName
import com.degrin.bitcoinwallet.feature.wallet.presentation.view.WalletContent
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.WalletViewModel
import org.koin.androidx.compose.koinViewModel

object WalletScreen : Screen {

    override val screenName: String = defaultScreenName()

    @Composable
    override fun Content(navController: NavController, args: Bundle?) {
        val viewModel: WalletViewModel = koinViewModel()

        WalletContent(
            state = viewModel.viewModelState,
            onReloadClick = viewModel::reloadData,
        )
    }
}
