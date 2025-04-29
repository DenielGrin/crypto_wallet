package com.degrin.bitcoinwallet.ui.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.degrin.bitcoinwallet.core.navigation.data.Screens
import com.degrin.bitcoinwallet.core.navigation.utils.keyboard.clearFocusOnClick
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.WalletScreen
import com.degrin.bitcoinwallet.ui.components.bottomBar.view.BottomBarContent
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun Content() {
    val navController: NavHostController = rememberNavController()

    AppTheme {
        Scaffold(
            modifier = Modifier
                .clearFocusOnClick()
                .navigationBarsPadding()
                .statusBarsPadding(),
            containerColor = MaterialTheme.colorScheme.primary,
            bottomBar = {
                BottomBarContent(
                    navController = navController,
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = WalletScreen.screenName
                ) {
                    Screens.getAllScreens().forEach { screen ->
                        composable(
                            route = screen.screenName,
                            arguments = screen.navArgs
                        ) { backStackEntry ->
                            screen.Content(
                                navController = navController,
                                args = backStackEntry.arguments
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Content_Preview() {
    AppTheme {
        Content()
    }
}
