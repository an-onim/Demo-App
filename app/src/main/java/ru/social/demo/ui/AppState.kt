package ru.social.demo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.social.core.base.NavBarPath
import ru.social.core.base.NavPath

class AppState(
    private val navController: NavHostController
) {

    private val bottomBarTabs = NavBarPath.entries.toTypedArray()
    private val bottomBarPaths = bottomBarTabs.map { it.route }

    val isBottomBarVisible: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination?.parent?.route in bottomBarPaths
                && navController
            .currentBackStackEntryAsState()
            .value?.destination?.route == NavPath.MAIN

    val currentRoute: String?
        get() = navController.currentDestination?.route
    val parentRoute: String?
        get() = navController.currentDestination?.parent?.route

    fun navigateToBottomBarTab(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().route.toString()) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

}