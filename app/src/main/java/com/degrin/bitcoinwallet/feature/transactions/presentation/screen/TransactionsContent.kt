package com.degrin.bitcoinwallet.feature.transactions.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.navigation.utils.warnings.showToastError
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionType
import com.degrin.bitcoinwallet.feature.transactions.presentation.view.TransactionContentList
import com.degrin.bitcoinwallet.feature.transactions.presentation.view.TransactionEmptyState
import com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel.TransactionScreenState
import com.degrin.bitcoinwallet.ui.components.button.BaseRoundedButton
import com.degrin.bitcoinwallet.ui.components.errors.BaseErrorContainer
import com.degrin.bitcoinwallet.ui.components.header.WalletHeader
import com.degrin.bitcoinwallet.ui.components.progress.BasePulseLoader
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import java.math.BigDecimal

@Composable
fun TransactionsContent(
    state: TransactionScreenState,
    balance: BigDecimal?,
    onSendClick: () -> Unit,
    onReloadClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(Sizes.Paddings.dp16)),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp24)
    ) {

        WalletHeader(
            headerId = R.string.transactions_screen_title,
            balance = balance ?: BigDecimal(0.0),
        )
        Row(
            modifier = Modifier.padding(horizontal = Sizes.Paddings.dp32)
        ) {
            BaseRoundedButton(
                iconId = R.drawable.ic_arrow_up,
                textId = R.string.default_send_title,
                onClick = onSendClick,
            )
            Spacer(modifier = Modifier.weight(1f))
            BaseRoundedButton(
                iconId = R.drawable.ic_arrow_down,
                textId = R.string.default_receive_title,
                onClick = {
                    //TODO Implement receive action
                    showToastError(
                        context = context,
                        message = context.getString(R.string.not_implement_yet_title)
                    )
                },
            )
        }
        HorizontalDivider()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TransactionScreenState.Loading -> BasePulseLoader()

                is TransactionScreenState.Error -> BaseErrorContainer(
                    onReloadClick = onReloadClick,
                )

                is TransactionScreenState.NullableData -> TransactionEmptyState()
                is TransactionScreenState.Data -> TransactionContentList(
                    data = state.data,
                )

                else -> Unit
            }
        }
    }
}


@Preview(name = "TransactionsContent", showBackground = true)
@Composable
private fun PreviewTransactionsContent() {
    AppTheme {
        TransactionsContent(
            state = TransactionScreenState.Data(
                data = listOf(
                    TransactionSortedData(TransactionType.INCOMING, 0.001),
                    TransactionSortedData(TransactionType.OUTGOING, 0.0005),
                    TransactionSortedData(TransactionType.INTERNAL, 0.0),
                    TransactionSortedData(TransactionType.INCOMING, 0.002),
                )
            ),
            balance = BigDecimal(11110.0),
            onSendClick = {},
            onReloadClick = {},
        )
    }
}
