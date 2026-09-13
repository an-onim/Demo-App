package ru.social.demo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.social.core.base.NavBarPath
import ru.social.core.base.NavPath
import ru.social.core.ui.events.EventsPage

fun NavGraphBuilder.eventsFlow(
    navController: NavController
) {
    navigation(route = NavBarPath.EVENTS.route, startDestination = NavPath.MAIN) {
        composable(NavPath.MAIN) {
            EventsPage(navController)
        }
    }
}