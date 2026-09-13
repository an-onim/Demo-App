package ru.social.core.ui.common.components.appbars

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.social.core.ui.R
import ru.social.core.ui.common.components.Avatar
import ru.social.core.ui.common.components.appbars.utils.CollapsibleScaffold
import ru.social.core.ui.common.components.appbars.utils.CollapsibleTopAppBarScope
import ru.social.core.ui.common.components.appbars.utils.TopBar
import ru.social.core.ui.common.components.buttons.CIconButtonOutlined
import ru.social.core.ui.common.theme.SDTheme
import ru.social.demo.MainContract
import ru.social.demo.MainViewModel

/**
 * Collapsing AppBar with fading content and LazyColumn in body
 *
 * @param title String, name of the page
 * @param state State of list in columnContent
 * @param topBarContent Content in bar area, fading and disappearing while is being scrolled
 * @param columnContent LazyColumn (!) with scrolling content
 */

@Composable
fun CAppBar(
    title: String = "",
    state: LazyListState,
    navController: NavController? = null,
    topInset: Boolean = true,
    topBarContent: @Composable CollapsibleTopAppBarScope.() -> Unit,
    columnContent: @Composable (insets: PaddingValues) -> Unit
) {
    val mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
    val userViewState by mainViewModel.userViewState.observeAsState()

    CollapsibleScaffold(
        state = state,
        topInset = topInset,
        content = columnContent,
        topBar = {
            TopBar(
                modifier = Modifier
                    .clip(shape = SDTheme.shapes.appBarCorners)
                    .background(SDTheme.colors.bgSecondary),
                title = title,
                onBack = null,
                actions = {
                    CIconButtonOutlined(
                        iconId = R.drawable.ic_search,
                        bgColor = colorResource(R.color.white),
                        onClick = { }
                    )
                    CIconButtonOutlined(
                        iconId = R.drawable.ic_bell,
                        bgColor = colorResource(R.color.white),
                        onClick = { }
                    )
                    when (userViewState) {
                        is MainContract.State.SuccessUser -> Avatar(
                            size = 44.dp,
                            imgUrl = (userViewState as MainContract.State.SuccessUser).data?.imageUrl,
                            char = (userViewState as MainContract.State.SuccessUser).data?.name?.get(0),
                            onClick = { navController?.navigate(NavPath.PROFILE) }
                        )
                        else -> Avatar(size = 44.dp, onClick = { navController?.navigate(NavPath.PROFILE) })
                    }
                },
                content = topBarContent
            )
        }
    )

    LaunchedEffect(Unit) {
        Log.d("TEST", "AppBar, mainViewModel is $mainViewModel")
        if (userViewState !is MainContract.State.SuccessUser) {
            mainViewModel.handle(MainContract.Event.LoadUser)
        }
    }

}
