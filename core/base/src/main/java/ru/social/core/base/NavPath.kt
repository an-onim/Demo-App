package ru.social.core.base


object NavPath {

//    val wiki = NavBarPath.WIKI.route
//    val home = NavBarPath.HOME.route
//    val events = NavBarPath.EVENTS.route

    const val MAIN = "main"

    const val AUTH = "auth"
    const val PROFILE = "profile"

    const val WIKI_SECTION = "wikiSection"

}

enum class NavBarPath(val label: Int, val idActive: Int, val idInactive: Int, val route: String) {
    WIKI(R.string.wiki, R.drawable.wiki_filled, R.drawable.wiki, "wikiRoot"),
    HOME(R.string.home, R.drawable.home_filled, R.drawable.home, "homeRoot"),
    EVENTS(R.string.events, R.drawable.events_filled, R.drawable.events, "eventsRoot")
}
