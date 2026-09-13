package ru.social.demo.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.social.core.base.NavBarPath
import ru.social.core.base.NavPath
import ru.social.core.ui.wiki.main.WikiPage
import ru.social.core.ui.wiki.components.WikiTypeRes
import ru.social.core.ui.wiki.section.WIKI_SECTION_TYPE
import ru.social.core.ui.wiki.section.WikiSectionPage

fun NavGraphBuilder.wikiFlow(
    navController: NavController
) {
    navigation(route = NavBarPath.WIKI.route, startDestination = NavPath.MAIN) {
        composable(NavPath.MAIN) {
            WikiPage(navController)
        }
        composable(
            "${NavPath.WIKI_SECTION}/{${WIKI_SECTION_TYPE}}",
            arguments = listOf(
                navArgument(WIKI_SECTION_TYPE) { type = NavType.StringType }
            )
        ) {
            WikiSectionPage(
                type = WikiTypeRes.valueOf(it.arguments?.getString(WIKI_SECTION_TYPE) ?: ""),
                viewModel = hiltViewModel(),
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}