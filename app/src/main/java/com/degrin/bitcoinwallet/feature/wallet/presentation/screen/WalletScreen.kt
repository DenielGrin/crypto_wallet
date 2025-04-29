package com.degrin.bitcoinwallet.feature.wallet.presentation.screen

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName
import com.degrin.bitcoinwallet.feature.wallet.presentation.view.WalletContent
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.InputFieldsState
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.WalletViewModel
import org.koin.androidx.compose.koinViewModel

object WalletScreen : Screen {

    override val screenName: String = defaultScreenName()

    @Composable
    override fun Content(navController: NavController, args: Bundle?) {
        val viewModel: WalletViewModel = koinViewModel()
        val inputState: State<InputFieldsState> = viewModel.inputState.collectAsState()

        WalletContent(
            state = viewModel.viewModelState,
            inputState = inputState.value,
            onUpdateAddress = viewModel::onAddressChanged,
            onUpdateAmount = viewModel::onAmountChanged,
            onReloadClick = viewModel::reloadData,
            onSendClick = viewModel::sendTransaction,
        )
    }
}
