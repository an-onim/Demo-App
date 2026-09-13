package ru.social.core.ui.wiki.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.social.demo.R
import ru.social.core.base.data.model.rpg.RpgClass
import ru.social.core.base.data.model.rpg.RpgRace
import ru.social.core.base.data.model.rpg.RpgTab
import ru.social.core.base.data.model.rpg.ShortInfo
import ru.social.core.ui.common.components.EmptyPage
import ru.social.demo.pages.wiki_section.WikiSectionContract
import ru.social.demo.ui.components.ArrowTile

@Composable
fun AllList(state: WikiSectionContract.State?, onClick: (RpgTab, String?) -> Unit) {
    if (state == null || state.isEmptyState()) {
        EmptyState("data")
    } else {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(state.classes) {
                ArrowTile(
                    title = "${it.name}",
                    description = "Class",
                    icon = null,
                    onClick = { onClick.invoke(RpgTab.CLASS, it.id) }
                )
            }
            items(state.races) {
                ArrowTile(
                    title = "${it.name}",
                    description = "Race",
                    icon = null,
                    onClick = { onClick.invoke(RpgTab.RACE, it.id) })
            }
            items(state.monsters ?: emptyList()) {
                ArrowTile(
                    title = "${it.name}",
                    description = "Monster",
                    icon = null,
                    onClick = { onClick.invoke(RpgTab.MONSTER, it.id) })
            }
        }
    }
}

@Composable
fun ClassesList(list: List<RpgClass>?, onClick: (RpgTab, String?) -> Unit) {
    if (list.isNullOrEmpty()) {
        EmptyState("classes")
    } else {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(list) {
                ArrowTile(
                    title = "${it.name}",
                    description = "Class",
                    icon = null,
                    onClick = { onClick.invoke(RpgTab.CLASS, it.id) })
            }
        }
    }
}

@Composable
fun RacesList(list: List<RpgRace>?, onClick: (RpgTab, String?) -> Unit) {
    if (list.isNullOrEmpty()) {
        EmptyState("races")
    } else {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(list) {
                ArrowTile(
                    title = "${it.name}",
                    description = "Race",
                    icon = null,
                    onClick = { onClick.invoke(RpgTab.RACE, it.id) })
            }
        }
    }
}

@Composable
fun MonstersList(list: List<ShortInfo>?, onClick: (RpgTab, String?) -> Unit) {
    if (list.isNullOrEmpty()) {
        EmptyState("monsters")
    } else {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(list) {
                ArrowTile(
                    title = "${it.name}",
                    description = "Monster",
                    icon = null,
                    onClick = { onClick.invoke(RpgTab.MONSTER, it.id) })
            }
        }
    }
}

@Composable
private fun EmptyState(type: String) {
    EmptyPage(
        stringResource(R.string.empty_title),
        String.format(stringResource(R.string.no_desc), type)
    )
}
