package com.degrin.bitcoinwallet.ui.view.bottomBar

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.degrin.bitcoinwallet.core.navigation.utils.navigateSingleTop
import com.degrin.bitcoinwallet.core.navigation.utils.navigateToTab
import com.degrin.bitcoinwallet.core.utils.customRippleClick
import com.degrin.bitcoinwallet.feature.transactions.presentation.screen.TransactionScreen
import com.degrin.bitcoinwallet.ui.theme.AppTheme

@Composable
fun BottomBarContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backPressedCallback = remember {

        object : OnBackPressedCallback(true) {
            private var backPressedOnce = false

            override fun handleOnBackPressed() {
                val currentScreen = navBackStackEntry?.destination?.route
                val isDashboard = currentScreen?.contains(TransactionScreen.screenName)
                when {
                    currentScreen == null -> {
                        (context as? Activity)?.finish()
                    }

                    isDashboard == true -> {
                        if (backPressedOnce) {
                            (context as? Activity)?.finish()
                        } else {
                            backPressedOnce = true
                            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    else -> {
                        backPressedOnce = false
                        navController.navigateSingleTop(TransactionScreen.screenName)
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
            .padding(PaddingValues(horizontal = 16.dp))
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(size = 40.dp)
                )
                .shadow(4.dp, shape = RoundedCornerShape(40.dp))
                .fillMaxWidth()
                .height(72.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(size = 40.dp)
                )
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf(
                BottomBarItem.TransactionsTab,
                BottomBarItem.DAppsTab,
                BottomBarItem.SwapTab,
                BottomBarItem.SettingsTab,
            ).forEach { item ->
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .customRippleClick(
                            onClick = {
                                if (item.screenName != navController.currentBackStackEntry?.destination?.route) {
                                    navController.navigateToTab(
                                        item.screenName,
                                        saveState = true
                                    )
                                }
                            }
                        )
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        painter = painterResource(item.iconId),
                        contentDescription = stringResource(item.labelId),
                        tint = when {
                            currentRoute == item.screenName -> MaterialTheme.colorScheme.surfaceVariant
                            else -> MaterialTheme.colorScheme.outline
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
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