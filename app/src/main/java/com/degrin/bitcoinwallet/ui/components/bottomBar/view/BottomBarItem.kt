package com.degrin.bitcoinwallet.ui.components.bottomBar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.degrin.bitcoinwallet.ui.sizes.Sizes

@Composable
fun BottomBarItem(
    isSelected: Boolean,
    labelId: Int,
    iconId: Int,
    modifier: Modifier = Modifier
) {
    val itemColor = when {
        isSelected -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.outline
    }
    Column(
        modifier = modifier
            .width(Sizes.Size.dp48)
            .height(Sizes.Size.dp48),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .width(Sizes.Size.dp24)
                .height(Sizes.Size.dp24),
            painter = painterResource(iconId),
            contentDescription = stringResource(labelId),
            tint = itemColor
        )
        Text(
            text = stringResource(labelId),
            style = MaterialTheme.typography.displaySmall.copy(color = itemColor)
        )
    }
}