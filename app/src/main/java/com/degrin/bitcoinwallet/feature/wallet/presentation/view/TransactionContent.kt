package com.degrin.bitcoinwallet.feature.wallet.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TransactionContent(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "TransactionContent")
    }
}

@Preview(name = "TransactionContent")
@Composable
private fun PreviewTransactionContent() {
    TransactionContent()
}