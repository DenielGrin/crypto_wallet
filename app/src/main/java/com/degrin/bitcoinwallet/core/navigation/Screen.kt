package com.degrin.bitcoinwallet.core.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController

interface Screen {
    val screenName: String
    val navArgs: List<NamedNavArgument> get() = emptyList()

    @Composable
    fun Content(navController: NavController, args: Bundle?)
}