package ru.social.core.ui.wiki.section

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import ru.social.demo.R
import ru.social.core.ui.common.components.EmptyPage
import ru.social.core.ui.wiki.components.WikiTypeRes
import ru.social.demo.ui.components.appbars.CTopBar
import ru.social.demo.ui.components.buttons.FilterButton

const val WIKI_SECTION_TYPE = "wikiSectionType"

@Composable
fun WikiSectionPage(
    type: WikiTypeRes,
    viewModel: WikiSectionViewModel,
    navigateBack: () -> Unit
) {
    var isFilterTabsVisible by remember { mutableStateOf(false) }

    CTopBar(
        title = stringResource(type.titleId),
        bgColor = colorResource(type.color),
        onBack = navigateBack,
        actions = {
            FilterButton(onClick = { isFilterTabsVisible = !isFilterTabsVisible })
        },
        content = { insets ->
            when(type) {
                WikiTypeRes.COMPENDIUM -> CompendiumPage(insets, isFilterTabsVisible, viewModel)
                else -> EmptyPage(
                    stringResource(R.string.empty_title),
                    String.format(stringResource(R.string.no_desc), "data")
                )
            }

        }
    )

}
