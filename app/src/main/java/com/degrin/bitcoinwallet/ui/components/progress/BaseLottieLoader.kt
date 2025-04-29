package com.degrin.bitcoinwallet.ui.components.progress

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun BaseLottieLoader(
    modifier: Modifier = Modifier,
    size: Dp = 300.dp
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.app_loader))
    val progress by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    LottieAnimation(
        modifier = modifier.size(size),
        composition = composition,
        progress = { progress }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BaseLottieLoader_Preview() {
    AppTheme {
        BaseLottieLoader()
    }
}
