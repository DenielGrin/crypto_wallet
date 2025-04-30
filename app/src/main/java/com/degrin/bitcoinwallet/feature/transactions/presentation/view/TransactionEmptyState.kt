package com.degrin.bitcoinwallet.feature.transactions.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.accessibility.Accessibility
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography

@Composable
fun TransactionEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_empty_transaction
            ),
            contentDescription = Accessibility.COIN_IMAGE
        )
        Text(
            text = stringResource(R.string.transaction_empty_state_title),
            style = getAppThemeTypography().displayLarge
        )
        Text(
            text = stringResource(R.string.transaction_empty_state_subtitle),
            style = getAppThemeTypography().displayMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    name = "PreviewTransactionEmptyState",
    showBackground = true
)
@Composable
private fun PreviewTransactionEmptyState() {
    AppTheme {
        TransactionEmptyState()
    }
}
