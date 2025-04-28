package com.degrin.bitcoinwallet

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.degrin.bitcoinwallet.core.navigation.data.Screens
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionScreen
import com.degrin.bitcoinwallet.ui.theme.AppTheme
import com.degrin.bitcoinwallet.ui.view.bottomBar.BottomBarContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            Content()
        }
    }
}

@Composable
private fun Content() {
    val navController: NavHostController = rememberNavController()

    AppTheme {
        Scaffold(
            modifier = Modifier
                .imePadding()
                .navigationBarsPadding()
                .statusBarsPadding(),
            containerColor = MaterialTheme.colorScheme.primary,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = TransactionScreen.screenName
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
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    BottomBarContent(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
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
