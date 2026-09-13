package ru.social.demo.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.social.core.base.NavBarPath
import ru.social.core.base.NavPath
import ru.social.core.ui.home.HomePage
import ru.social.core.ui.home.HomeViewModel
import ru.social.core.ui.user.profile.ProfilePage


fun NavGraphBuilder.homeFlow(
    navController: NavController
) {
    navigation(route = NavBarPath.HOME.route, startDestination = NavPath.MAIN) {
        composable(NavPath.MAIN) {
            val parentEntry = remember(it) { navController.getBackStackEntry(NavPath.MAIN) }
            val homeViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            HomePage(navController, homeViewModel)
        }
        composable(NavPath.PROFILE) {
            ProfilePage(
                navController = navController
            )
        }

    }
}