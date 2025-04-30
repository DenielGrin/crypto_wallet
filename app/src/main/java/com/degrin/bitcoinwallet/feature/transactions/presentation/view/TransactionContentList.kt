package com.degrin.bitcoinwallet.feature.transactions.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData
import com.degrin.bitcoinwallet.ui.sizes.Sizes

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
