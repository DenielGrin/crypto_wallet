package com.degrin.bitcoinwallet.ui.components.bottomBar.view

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.degrin.bitcoinwallet.R
import com.degrin.bitcoinwallet.core.navigation.utils.compose.customRippleClick
import com.degrin.bitcoinwallet.core.navigation.utils.navController.navigateSingleTop
import com.degrin.bitcoinwallet.core.navigation.utils.navController.navigateToTab
import com.degrin.bitcoinwallet.core.navigation.utils.warnings.showToastError
import com.degrin.bitcoinwallet.feature.wallet.presentation.screen.WalletScreen
import com.degrin.bitcoinwallet.ui.components.bottomBar.viewModel.BarItem
import com.degrin.bitcoinwallet.ui.sizes.Sizes
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun BottomBarContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val backPressedCallback = remember {

        object : OnBackPressedCallback(true) {
            private var backPressedOnce = false

            override fun handleOnBackPressed() {
                val currentScreen = navBackStackEntry?.destination?.route
                val isDashboard = currentScreen?.contains(WalletScreen.screenName)
                when {
                    currentScreen == null -> (context as? Activity)?.finish()

                    isDashboard == true -> if (backPressedOnce) {
                        (context as? Activity)?.finish()
                    } else {
                        backPressedOnce = true
                        showToastError(
                            context = context,
                            message = "Press back again to exit"
                        )
                    }

                    else -> {
                        backPressedOnce = false
                        navController.navigateSingleTop(WalletScreen.screenName)
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        backPressedDispatcher?.addCallback(backPressedCallback)
        onDispose { backPressedCallback.remove() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingValues(all = Sizes.Paddings.dp16))
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = Sizes.BorderSizes.dp1,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(size = Sizes.Size.dp40)
                )
                .shadow(4.dp, shape = RoundedCornerShape(Sizes.Size.dp40))
                .fillMaxWidth()
                .height(Sizes.Size.dp74)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(size = Sizes.Size.dp40)
                )
                .padding(horizontal = Sizes.Paddings.dp12),
            horizontalArrangement = Arrangement.spacedBy(Sizes.Paddings.dp8),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf(
                BarItem.WalletTab,
                BarItem.TransactionsTab,
                BarItem.DAppsTab,
                BarItem.SettingsTab,
            ).forEach { item ->
                BottomBarItem(
                    isSelected = currentRoute == item.screenName,
                    modifier = Modifier
                        .customRippleClick(
                            onClick = {
                                if (item.screenName != navController.currentBackStackEntry?.destination?.route) {
                                    when (item.screenName) {
                                        null -> showToastError(
                                            context = context,
                                            message = context.getString(R.string.not_implement_yet_title)
                                        )

                                        else -> navController.navigateToTab(
                                            screenName = item.screenName,
                                            saveState = true
                                        )
                                    }
                                }
                            }
                        )
                        .weight(1f),
                    labelId = item.labelId,
                    iconId = item.iconId
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BottomBarContent_Preview() {
    AppTheme {
        BottomBarContent(
            navController = rememberNavController()
        )
    }
}