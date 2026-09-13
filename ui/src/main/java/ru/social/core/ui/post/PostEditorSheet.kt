package ru.social.core.ui.post

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import ru.social.demo.MainContract
import ru.social.demo.MainViewModel
import ru.social.demo.R
import ru.social.core.base.data.model.Post
import ru.social.demo.services.FirestoreClient
import ru.social.demo.services.FsPath
import ru.social.demo.ui.components.CBottomSheet
import ru.social.demo.ui.components.buttons.CIconButton
import ru.social.demo.ui.components.buttons.CTextButton
import ru.social.demo.ui.components.text.CTextField
import ru.social.demo.ui.theme.SDTheme
import ru.social.demo.utils.ImageUtils
import ru.social.demo.utils.NetworkUtils
import ru.social.demo.utils.toBase64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostEditorSheet(
    isCreateMode: Boolean = false,
    type: Post.Type = Post.Type.POST,
    post: Post = Post(type = type),
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {}
) {
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel(context as ComponentActivity)
    val userViewState by mainViewModel.userViewState.observeAsState()

    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    val typee = post?.type ?: type
    val mediaUrls = remember { mutableStateListOf<String>() }
    val mediaUris = remember { mutableStateListOf<Uri>() }

    title.value = post.title ?: ""
    text.value = post.text ?: ""
    post.media?.let { media -> mediaUrls.addAll(media) }
    post.mediaBase64?.let { media ->
        mediaUris.addAll(media.mapNotNull { m -> ImageUtils.base64ToUri(m) })
    }

    fun prepareData() =
        Post(
            createDate = Timestamp.now(),
            type = typee,
            title = title.value,
            text = text.value,
            media = mediaUrls,
            mediaBase64 = mediaUris.map { it.toBase64(context) },
            user = (userViewState as? MainContract.State.SuccessUser)?.data
        )

    fun prepareCopy() =
        post.copy(
            updateDate = Timestamp.now(),
            type = typee,
            title = title.value,
            text = text.value,
            media = mediaUrls,
            mediaBase64 = mediaUris.map { it.toBase64(context) },
            user = (userViewState as? MainContract.State.SuccessUser)?.data
        )

    CBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = stringResource(typee.idString),
        onBack = onDismissRequest,
        actions = {
            if (isCreateMode) {
                CTextButton(
                    label = stringResource(R.string.post_create),
                    enabled = text.value.isNotEmpty(),
                    onClick = {
                        onDismissRequest()
                        NetworkUtils.makeCallIO {
                            FirestoreClient.getInstance()
                                .setData(FsPath.POSTS, prepareData())
                        }
                    }
                )
            } else {
                CTextButton(
                    label = stringResource(R.string.post_edit),
                    enabled = text.value.isNotEmpty(),
                    onClick = {
                        onDismissRequest()
                        NetworkUtils.makeCallIO {
                            FirestoreClient.getInstance()
                                .updateData(FsPath.POSTS, prepareCopy())
                        }
                    }
                )
            }
        }
    ) {

        Column {
            CTextField(
                value = title.value,
                textStyle = SDTheme.typography.headingS,
                singleLine = true,
                onValueChange = { title.value = it }
            )
            CTextField(
                modifier = Modifier.weight(1f),
                value = text.value,
                onValueChange = { text.value = it }
            )

            AttachmentsList(mediaUrls, mediaUris)
            BottomMenu(uris = mediaUris)
        }

    }

}

@Composable
private fun AttachmentsList(urls: MutableList<String>, uris: MutableList<Uri>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(urls) {
            AttachmentMedia(it) { urls.remove(it) }
        }

        items(uris) {
            AttachmentMedia(it) { uris.remove(it) }
        }
    }
}

@Composable
private fun BottomMenu(uris: MutableList<Uri>) {
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
//        if (it) {
//          uris.add()
//        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        uris.addAll(it)
    }

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        CIconButton(
            size = 44.dp,
            iconId = R.drawable.ic_camera_plus,
            contentColor = SDTheme.colors.fgSecondary
        ) {

        }

        CIconButton(
            size = 44.dp,
            iconId = R.drawable.ic_image,
            contentColor = SDTheme.colors.fgSecondary
        ) {
            galleryLauncher.launch("image/*")
        }
    }
}