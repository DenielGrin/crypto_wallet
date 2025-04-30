package com.degrin.bitcoinwallet.ui.components.errors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.accessibility.Accessibility.ERROR_IMAGE
import com.degrin.bitcoinwallet.ui.components.button.BaseButton
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.theme.getAppThemeTypography

@Composable
fun BaseErrorContainer(
    modifier: Modifier = Modifier,
    titleId: Int = R.string.default_error_empty_state_title,
    subTitleId: Int = R.string.default_error_empty_state_subtitle,
    onReloadClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(Sizes.Size.dp110)
                .height(Sizes.Size.dp110)
                .padding(bottom = Sizes.Paddings.dp24),
            painter = painterResource(R.drawable.ic_error),
            contentDescription = ERROR_IMAGE
        )

        Text(
            text = stringResource(titleId),
            textAlign = TextAlign.Center,
            style = getAppThemeTypography().headlineMedium.copy(
                fontWeight = W700
            )
        )

        Spacer(modifier = Modifier.height(Sizes.Size.dp8))
        Text(
            text = stringResource(subTitleId),
            textAlign = TextAlign.Center,
            style = getAppThemeTypography().titleSmall
        )

        Spacer(modifier = Modifier.height(Sizes.Size.dp24))
        BaseButton(
            buttonTextId = R.string.default_retry_title,
            radius = Sizes.CornerShape.dp10,
            onClick = onReloadClick
        )
    }
}

@Preview(name = "BaseErrorContainer")
@Composable
private fun PreviewBaseErrorContainer() {
    AppTheme {
        BaseErrorContainer(
            onReloadClick = {}
        )
    }
}
