package com.degrin.bitcoinwallet.feature.wallet.presentation.screen

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.core.navigation.utils.compose.observeAsActions
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName
import com.degrin.bitcoinwallet.core.navigation.utils.navController.navigateToTab
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionsScreen
import com.degrin.bitcoinwallet.feature.wallet.presentation.view.WalletTransactionDialog
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.WalletViewModel
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.InputFieldsState
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.state.SendingDialogState
import org.koin.androidx.compose.koinViewModel

object WalletScreen : Screen {

    override val screenName: String = defaultScreenName()

    @Composable
    override fun Content(navController: NavController, args: Bundle?) {
        val viewModel: WalletViewModel = koinViewModel()
        val inputState: State<InputFieldsState> = viewModel.inputState.collectAsState()
        var dialogState: SendingDialogState by remember { mutableStateOf(SendingDialogState.None) }

        viewModel.actions.observeAsActions { action ->
            dialogState = when (action) {
                is WalletViewModel.Actions.ErrorSendingCoins -> {
                    SendingDialogState.Error(message = action.message)
                }

                is WalletViewModel.Actions.SuccessSendingCoins -> {
                    SendingDialogState.Success(id = action.id)
                }
            }
        }

        HandlerDialogState(
            dialogState = dialogState,
            navController = navController,
            onDismissRequest = {
                dialogState = SendingDialogState.None
            }
        )

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

@Composable
private fun HandlerDialogState(
    dialogState: SendingDialogState,
    navController: NavController,
    onDismissRequest: () -> Unit
) {
    if (dialogState != SendingDialogState.None) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            WalletTransactionDialog(
                isSuccessState = dialogState is SendingDialogState.Success,
                id = (dialogState as? SendingDialogState.Success)?.id,
                onButtonClick = {
                    if (dialogState is SendingDialogState.Success) {
                        navController.navigateToTab(TransactionsScreen.defaultScreenName())
                    }
                    onDismissRequest()
                }
            )
        }
    }
}
