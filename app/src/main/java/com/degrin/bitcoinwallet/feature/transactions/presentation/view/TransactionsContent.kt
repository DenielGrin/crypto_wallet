package com.degrin.bitcoinwallet.feature.transactions.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData
import com.degrin.bitcoinwallet.feature.transactions.presentation.viewModel.TransactionScreenState
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(Sizes.Paddings.dp16)),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp16)
    ) {
        WalletHeader(
            headerId = R.string.transactions_screen_title,
            balance = balance?: BigDecimal(0.0),
        )
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
                is TransactionScreenState.Data -> TransactionContentData(
                    data = state.data,
                )

                else -> Unit
            }
        }
    }
}

@Composable
fun TransactionContentData(data: List<TransactionSortedData>) {

}

@Preview(name = "TransactionsContent", showBackground = true)
@Composable
private fun PreviewTransactionsContent() {
    AppTheme {
        TransactionsContent(
            state = TransactionScreenState.Loading,
            balance = BigDecimal(0.0),
            onSendClick = {},
            onReloadClick = {},
        )
    }
}
