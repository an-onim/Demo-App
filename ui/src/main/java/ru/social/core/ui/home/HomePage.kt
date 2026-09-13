package ru.social.core.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.social.demo.MainViewModel
import ru.social.demo.R
import ru.social.core.network.model.Post
import ru.social.core.ui.common.components.EmptyPage
import ru.social.core.ui.post.PostEditorSheet
import ru.social.core.ui.post.PostTile
import ru.social.demo.ui.components.CProgressIndicator
import ru.social.demo.ui.components.appbars.CAppBar
import ru.social.demo.ui.components.buttons.fab.Fab
import ru.social.demo.ui.components.buttons.fab.FabItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)

    val viewState by viewModel.postsViewState.observeAsState()
    val postsListState = rememberLazyListState()

    Scaffold(
        floatingActionButton = {
            FabButton(
                reloadData = { viewModel.handle(HomeContract.Event.Reload) }
            )
        }
    ) { _ ->
        CAppBar(
            title = stringResource(R.string.home),
            state = postsListState,
            navController = navController,
            topBarContent = { Carousel() },
            columnContent = { insets ->
                when (viewState) {
                    is HomeContract.State.LoadingFeed -> CProgressIndicator()
                    is HomeContract.State.SuccessFeed -> Feed(
                        (viewState as HomeContract.State.SuccessFeed).data,
                        insets,
                        postsListState,
                        viewModel
                    )

                    else -> EmptyPage(
                        title = "Oops!",
                        description = stringResource(R.string.error_loading_desc)
                    )
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        Log.d("TEST", "Home, viewModel is $viewModel, mainVM $mainViewModel")
        viewModel.handle(HomeContract.Event.LoadFeed)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Feed(
    data: List<Post>?,
    insets: PaddingValues,
    postsListState: LazyListState,
    viewModel: HomeViewModel
) {
    val postToEditState by viewModel.postToEditState.observeAsState()

    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        state = postsListState,
        contentPadding = insets
    ) {
        if (data.isNullOrEmpty()) {
            item {
                EmptyPage(
                    title = "Feed",
                    description = stringResource(R.string.no_posts_decs)
                )
            }
        } else {
            items(data) { post ->
                PostTile(
                    post,
                    onEdit = //if (post.isMine())
                        {
                            coroutineScope.launch {
                                viewModel.handle(HomeContract.Event.EditPostClicked(post))
                                isBottomSheetVisible = true
                                sheetState.expand()
                            }
                        }
                )
                HorizontalDivider(thickness = 10.dp, color = Color.Transparent)
            }
        }
    }

    (postToEditState as? HomeContract.State.PostToEdit)?.data?.let {
        PostEditorSheet(
            post = it,
            isBottomSheetVisible = isBottomSheetVisible,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope
                    .launch {
                        sheetState.hide()
                        viewModel.handle(HomeContract.Event.EditPostClicked(null))
                    }
                    .invokeOnCompletion { isBottomSheetVisible = false }

            }
        )
    }


}

@Composable
private fun Carousel() {
    Column(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(156.dp)
        ) {
            Text(
                text = "News, notifications and stories as list of cards",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FabButton(reloadData: () -> Unit) {
    val type = remember { mutableStateOf(Post.Type.POST) }
    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Fab(
        items = listOf(
            object : FabItem(
                id = "new_post_default",
                iconId = R.drawable.ic_plus_circle,
                label = R.string.post_type_default
            ) {
                override fun onClick() {
                    coroutineScope.launch {
                        type.value = Post.Type.POST
                        isBottomSheetVisible = true
                        sheetState.expand()
                    }
                }
            },
            object : FabItem(
                id = "new_post_event",
                iconId = R.drawable.ic_calendar,
                label = R.string.post_type_event
            ) {
                override fun onClick() {
                    coroutineScope.launch {
                        type.value = Post.Type.EVENT
                        isBottomSheetVisible = true
                        sheetState.expand()
                    }
                }
            }
        )
    )

    PostEditorSheet(
        isCreateMode = true,
        type = type.value,
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = {
            coroutineScope
                .launch { sheetState.hide() }
                .invokeOnCompletion {
                    isBottomSheetVisible = false
                    reloadData.invoke()
                }
        }
    )
}
