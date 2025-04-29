package com.degrin.bitcoinwallet.feature.transactions.presentation.screen

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName

object TransactionsScreen : Screen {

    override val screenName: String = defaultScreenName()

    @Composable
    override fun Content(navController: NavController, args: Bundle?) {

    }
}
