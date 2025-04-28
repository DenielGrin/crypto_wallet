package com.degrin.bitcoinwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.degrin.bitcoinwallet.core.navigation.data.Screens
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionScreen
import com.degrin.bitcoinwallet.ui.theme.BitcoinWalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Content()
        }
    }
}

@Composable
private fun Content() {
    val navController: NavHostController = rememberNavController()

    BitcoinWalletTheme {

        Scaffold(
            modifier = Modifier
                .imePadding()
                .navigationBarsPadding()
                .statusBarsPadding(),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Box(
                modifier = Modifier.padding(it),
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
            }
        }
    }
}
