package ru.social.demo.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.social.core.base.NavBarPath
import ru.social.core.base.NavPath
import ru.social.core.ui.user.auth.AuthPage

fun NavGraphBuilder.authFlow(
    navController: NavController
) {

    composable(route = NavPath.AUTH) {
        AuthPage(
            { navController.navigate(NavBarPath.HOME.route) },
            viewModel = hiltViewModel()
        )
    }

}