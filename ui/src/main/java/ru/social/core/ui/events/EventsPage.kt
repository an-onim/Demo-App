package ru.social.core.ui.events

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.social.demo.R
import ru.social.core.ui.common.components.EmptyPage
import ru.social.core.ui.common.components.appbars.CAppBar
import ru.social.demo.ui.components.appbars.CAppBar

@Composable
fun EventsPage(navController: NavController) {

    val listState = rememberLazyListState()
    CAppBar(
        title = stringResource(R.string.events),
        state = listState,
        navController = navController,
        topBarContent = {
//            val fraction = this.fraction
//            Column(
//                modifier = Modifier
//                    .padding(bottom = 32.dp)
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                Box(modifier = Modifier.fillMaxWidth().padding(8.dp).height(156.dp)) {
//                    Text(
//                        text = "Card",
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxSize()
//                    )
//                }
//            }
        },
        columnContent = { insets ->
            LazyColumn(
                state = listState,
                contentPadding = insets
            ) {
                item {
                    EmptyPage(
                        stringResource(R.string.empty_title),
                        "Page is under construction"
                    )
                }
            }
        }
    )

}