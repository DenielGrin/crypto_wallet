package com.degrin.bitcoinwallet.feature.transactionDetails.presentation.screen

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.degrin.bitcoinwallet.core.navigation.Screen
import com.degrin.bitcoinwallet.core.navigation.utils.navController.defaultScreenName

object TransactionDetailsScreen : Screen {

    override val screenName: String = defaultScreenName()

    @Composable
    override fun Content(navController: NavController, args: Bundle?) {

    }
}
