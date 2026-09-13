package ru.social.core.ui.wiki.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.social.demo.R
import ru.social.core.base.NavPath
import ru.social.core.ui.wiki.components.WikiAppBarTile
import ru.social.core.ui.wiki.components.WikiTile
import ru.social.core.ui.wiki.components.WikiTypeRes
import ru.social.demo.pages.wiki_section.WIKI_SECTION_TYPE
import ru.social.demo.ui.components.appbars.CAppBar
import ru.social.demo.ui.components.buttons.fab.Fab
import ru.social.demo.ui.components.buttons.fab.FabItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WikiPage(
    navController: NavController
) {
    val listState = rememberLazyListState()

    Scaffold(
        floatingActionButton = { FabButton() }
    ) { _ ->
        CAppBar(
            title = stringResource(R.string.wiki),
            state = listState,
            navController = navController,
            topBarContent = { TopBarContent() },
            columnContent = { insets ->
                LazyColumn(
                    state = listState,
                    contentPadding = insets
                ) {
                    items(WikiTypeRes.entries.toTypedArray()) { type ->
                        WikiTile(
                            type = type,
                            onClick = {
                                navController
                                    .apply {
                                        Bundle().apply {
                                            putString(WIKI_SECTION_TYPE, type.toString())
                                        }
                                    }
                                    .navigate("${NavPath.WIKI_SECTION}/$type")
                            }
                        )
                        HorizontalDivider(thickness = 10.dp, color = Color.Transparent)
                    }

                    item { Spacer(Modifier.size(90.dp)) }
                }
            }
        )
    }
}


@Composable
private fun TopBarContent() {
    Row(
        modifier = Modifier
            .padding(bottom = 32.dp, top = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WikiAppBarTile(
            imageId = R.drawable.ic_media,
            titleId = R.string.wiki_media,
            descriptionId = R.string.wiki_media_desc,
            modifier = Modifier.weight(1f)
        )
        WikiAppBarTile(
            imageId = R.drawable.ic_folders,
            titleId = R.string.wiki_folders,
            descriptionId = R.string.wiki_folders_desc,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FabButton() {
    Fab(
        items = listOf(
            object : FabItem(
                id = "new_post_default",
                iconId = R.drawable.ic_plus_circle,
                label = R.string.post_type_default
            ) {
                override fun onClick() { }
            },
            object : FabItem(
                id = "new_post_event",
                iconId = R.drawable.ic_calendar,
                label = R.string.post_type_event
            ) {
                override fun onClick() { }
            },
            object : FabItem(
                id = "new_post_event",
                iconId = R.drawable.ic_user_edit,
                label = R.string.post_type_character
            ) {
                override fun onClick() { }
            }
        )
    )
}
