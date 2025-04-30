package com.degrin.bitcoinwallet.feature.transactions.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionType
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun TransactionContentList(data: List<TransactionSortedData>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp16),
    ) {
        data.forEach {
            TransactionItem(transaction = it)
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TransactionContentList_Preview() {
    AppTheme {
        TransactionContentList(
            data = listOf(
                TransactionSortedData(TransactionType.INCOMING, 0.001),
                TransactionSortedData(TransactionType.OUTGOING, 0.0005),
                TransactionSortedData(TransactionType.INTERNAL, 0.0),
                TransactionSortedData(TransactionType.INCOMING, 0.002),
            )
        )
    }
}