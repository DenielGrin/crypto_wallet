package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.feature.wallet.presentation.viewModel.InputFieldsState
import com.degrin.bitcoinwallet.ui.components.button.BaseButton
import com.degrin.bitcoinwallet.ui.components.inputField.BaseTextField
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun WalletInputFields(
    modifier: Modifier = Modifier,
    inputState: InputFieldsState,
    onUpdateAddress: (String) -> Unit,
    onUpdateAmount: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp16)
    ) {
        BaseTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputState.amount,
            isError = inputState.amountError != null,
            errorText = inputState.amountError?.let { stringResource(it) },
            hint = stringResource(R.string.wallet_screen_input_amount_hint),
            onValueChange = onUpdateAmount,
        )
        BaseTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputState.address,
            hint = stringResource(R.string.wallet_screen_input_address_hint),
            isError = inputState.addressError != null,
            errorText = inputState.addressError?.let { stringResource(it) },
            onValueChange = onUpdateAddress,
        )
        Spacer(modifier = Modifier.weight(1f))
        BaseButton(
            modifier = Modifier
                .height(Sizes.Size.dp50)
                .fillMaxWidth(),
            buttonTextId = R.string.wallet_screen_button_send_title,
            onClick = onSendClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransactionContent() {
    AppTheme {
        WalletInputFields(
            inputState = InputFieldsState(
                amountError = R.string.error_invalid_amount_empty,
            ),
            onUpdateAddress = {},
            onUpdateAmount = {},
            onSendClick = {}
        )
    }
}