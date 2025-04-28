package com.degrin.bitcoinwallet.core.navigation.utils.navController

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun <T> NavController.saveResultToBackStackScreen(key: String, value: T) {
    this.previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)
}

fun NavController.navigateToTab(
    screenName: String,
    saveState: Boolean = true
) {
    navigate(screenName) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            this.saveState = saveState
        }
        // Avoid multiple copies of the same destination when
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

fun NavController.navigateSingleTop(
    route: String
) {
    navigate(route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateSingle(
    route: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    if (route != currentDestination?.route) {
        navigate(route, navOptions, navigatorExtras)
    }
}

fun NavController.navigateToScreen(screenName: String) {
    navigate(screenName)
}
