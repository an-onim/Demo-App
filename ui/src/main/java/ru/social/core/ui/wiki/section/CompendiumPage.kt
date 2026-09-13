package ru.social.core.ui.wiki.section

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import ru.social.core.network.model.rpg.RpgTab
import ru.social.core.ui.wiki.components.AllList
import ru.social.core.ui.wiki.components.ClassesList
import ru.social.core.ui.wiki.components.CompendiumDetailsSheet
import ru.social.core.ui.wiki.components.MonstersList
import ru.social.core.ui.wiki.components.RacesList
import ru.social.demo.ui.components.CTabRow
import ru.social.demo.ui.components.containers.RefreshContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompendiumPage(
    insets: PaddingValues,
    isTabsVisible: Boolean,
    viewModel: WikiSectionViewModel
) {
    val tabs = RpgTab.entries
    val dataViewState = viewModel.compendiumState.observeAsState()

    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun onItemClick(type: RpgTab, id: String?) {
        viewModel.handle(WikiSectionContract.Event.ItemClicked(type, id ?: ""))
        coroutineScope.launch {
            isBottomSheetVisible = true
            sheetState.expand()
        }
    }

    RefreshContainer(
        onRefresh = {
            val selectedTab = dataViewState.value?.selectedTab ?: 0
            viewModel.handle(WikiSectionContract.Event.Reload(tabs[selectedTab]))
        }
    ) {
        Column(
            Modifier
                .padding(insets)
                .fillMaxSize()) {

            AnimatedVisibility(isTabsVisible) {
                CTabRow(
                    tabs = tabs,
                    tabTitle = { item -> item.label() },
                    onClick = { index, item ->
                        if (dataViewState.value?.isDataEmpty(item) == true) {
                            viewModel.handle(WikiSectionContract.Event.LoadData(type = item))
                        }
                        viewModel.handle(WikiSectionContract.Event.TabChanged(idx = index))
                    }
                )

            }

            when(dataViewState.value?.selectedTab) {
                0 -> AllList(dataViewState.value, ::onItemClick)
                1 -> ClassesList(dataViewState.value?.classes, ::onItemClick)
                2 -> RacesList(dataViewState.value?.races, ::onItemClick)
                3 -> MonstersList(dataViewState.value?.monsters, ::onItemClick)
            }
        }
    }

    CompendiumDetailsSheet(
        data = dataViewState.value?.bottomSheetItem,
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = { coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion { isBottomSheetVisible = false }
        }
    )


    LaunchedEffect(Unit) {
        val selectedTab = dataViewState.value?.selectedTab ?: 0
        viewModel.handle(
            WikiSectionContract.Event.LoadData(tabs[selectedTab])
        )
    }

}
