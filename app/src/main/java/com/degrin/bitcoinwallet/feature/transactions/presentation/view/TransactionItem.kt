package com.degrin.bitcoinwallet.feature.transactions.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.navigation.utils.formatting.formatBtc
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionSortedData
import com.degrin.bitcoinwallet.feature.transactions.data.model.TransactionType
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography

@Composable
fun TransactionItem(transaction: TransactionSortedData) {
    val isIncoming = transaction.type == TransactionType.INCOMING
    val amountWithSign = when {
        isIncoming -> "+${formatBtc(transaction.amount)}"
        else -> "-${formatBtc(transaction.amount)}"
    }
    val color = when {
        isIncoming -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.onError
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Sizes.Paddings.dp16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Sizes.Size.dp24),
                painter = painterResource(
                    id = when {
                        isIncoming -> R.drawable.ic_arrow_down
                        else -> R.drawable.ic_arrow_up
                    }
                ),
                contentDescription = transaction.type.name,
                tint = color,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = transaction.type.name.lowercase()
                    .replaceFirstChar { it.uppercase() },
                style = getAppThemeTypography().displayMedium
            )
        }
        Text(
            text = amountWithSign,
            color = color,
            style = getAppThemeTypography().displayMedium
        )
    }
}

