package com.degrin.bitcoinwallet.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.degrin.bitcoinwallet.R

@Composable
fun getAppThemeColors(darkMode: Boolean): ColorScheme = when {
    darkMode -> darkColorScheme(
        primary = Color(0xFF081821),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFF2D9CCA),
        onPrimaryContainer = Color(0xFF1c2a33),
        secondary = Color(0xFF1c2a33),
        onSecondary = Color(0xFF909CA0),
        secondaryContainer = Color(0xFFf9f9f9),
        onSecondaryContainer = Color(0xFF787A78),
        tertiary = Color(0xFF0C2B38),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0x1A20D327),
        onTertiaryContainer = Color(0x1AFB7526),
        error = Color.Red,
        onError = Color(0xFFD32F2F),
        errorContainer = Color(0xFFFD3E3E),
        onErrorContainer = Color(0xFFd3302f),
        background = Color(0xFF0C2B38),
        onBackground = Color(0x1AF77726),
        surface = Color(0xFF294450),
        outline = Color(0xFF014361),
        surfaceVariant = Color(0xFF2976EA),
        onSurfaceVariant = Color(0xFF294450),
    )

    else -> lightColorScheme(
        primary = Color(0xFFF1F8FF),
        onPrimary = Color(0xFF000000),
        primaryContainer = Color(0xFF2D9CCA),
        onPrimaryContainer = Color(0xFFFFFFFF),
        secondary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFF909CA0),
        secondaryContainer = Color(0xFFf9f9f9),
        onSecondaryContainer = Color(0xFF787A78),
        tertiary = Color(0xFF25274D),
        onTertiary = Color(0xFF25274D),
        tertiaryContainer = Color(0x1A20D327),
        onTertiaryContainer = Color(0x1AFB7526),
        error = Color.Red,
        onError = Color(0xFFD32F2F),
        errorContainer = Color(0xFFEF5350),
        onErrorContainer = Color(0xFFd3302f),
        background = Color(0xFFFFFFFF),
        onBackground = Color(0x1AF77726),
        surface = Color(0xFFE5F6FD),
        outline = Color(0xFF014361),
        surfaceVariant = Color(0xFF2976EA),
        onSurfaceVariant = Color(0xFF404162),
    )
}

val robotoRegular = FontFamily(Font(R.font.roboto_regular))

@Composable
fun getAppThemeTypography(): Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = robotoRegular,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        color = MaterialTheme.colorScheme.onTertiary,
    ),
    displayMedium = TextStyle(
        fontFamily = robotoRegular,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = MaterialTheme.colorScheme.onTertiary
    ),
    displaySmall = TextStyle(
        fontFamily = robotoRegular,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        color = MaterialTheme.colorScheme.onTertiary,
    ),
    headlineLarge = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = robotoRegular,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        color = MaterialTheme.colorScheme.onTertiary
    ),
    headlineSmall = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        fontWeight = FontWeight.W700,
        fontSize = 40.sp,
        lineHeight = 46.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W500,
        fontSize = 10.sp,
        lineHeight = 10.sp
    ),
    labelLarge = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    labelMedium = TextStyle(
        fontFamily = robotoRegular,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = robotoRegular,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = MaterialTheme.colorScheme.onTertiary,
    ),
)

@Composable
fun BitcoinWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getAppThemeColors(darkMode = darkTheme)

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes(),
        typography = getAppThemeTypography(),
        content = content
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.apply {
                statusBarColor = colors.background.toArgb()
                navigationBarColor = colors.background.toArgb()
                WindowCompat.getInsetsController(
                    /* window = */ this,
                    /* view = */ view
                ).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }
}
