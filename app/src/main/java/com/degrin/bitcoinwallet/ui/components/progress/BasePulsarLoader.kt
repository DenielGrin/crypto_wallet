package com.degrin.bitcoinwallet.ui.components.progress

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.accessibility.Accessibility.LOADER_IMAGE
import com.degrin.bitcoinwallet.ui.sizes.Sizes

@Composable
fun BasePulseLoader(
    modifier: Modifier = Modifier,
    size: Dp = Sizes.Size.dp100
) {
    val scale by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 600,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Image(
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .background(Color.Transparent)
            .size(size),
        painter = painterResource(id = R.drawable.ic_app),
        contentDescription = LOADER_IMAGE,
    )
}
