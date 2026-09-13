package ru.social.library

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.social.app.data.model.Post
import ru.social.app.data.model.User
import ru.social.app.data.network.firebase.FirestoreClient
import ru.social.app.data.network.firebase.FsPath
import ru.social.core.ui.R
import ru.social.core.ui.common.components.ArrowTile
import ru.social.core.ui.common.components.Avatar
import ru.social.core.ui.common.components.CBottomSheet
import ru.social.core.ui.common.components.GalleryBlock
import ru.social.core.ui.common.components.buttons.CButton
import ru.social.core.ui.common.components.buttons.CIconButton
import ru.social.core.ui.common.components.buttons.CIconButtonOutlined
import ru.social.core.ui.common.components.buttons.COutlinedButton
import ru.social.core.ui.common.components.buttons.CSwitch
import ru.social.core.ui.common.components.buttons.CTextButton
import ru.social.core.ui.common.components.buttons.CTonalButton
import ru.social.core.ui.common.components.buttons.ChipButton
import ru.social.core.ui.common.components.buttons.ChipGroup
import ru.social.core.ui.common.components.containers.OutlinedContainer
import ru.social.core.ui.common.components.text.RoundedTextField
import ru.social.core.ui.common.theme.SDTheme
import ru.social.core.ui.post.PostEditorSheet
import ru.social.core.ui.wiki.components.WikiTile
import ru.social.core.ui.wiki.components.WikiTypeRes
import java.lang.reflect.Field
import java.util.Date
import kotlin.collections.get
import kotlin.jvm.java
import kotlin.random.Random

private val TEMP_USER = User(
    id = "0",
    name = "Maria Robbins",
    imageUrl = "https://media.istockphoto.com/id/1326417862/photo/young-woman-laughing-while-relaxing-at-home.jpg?s=612x612&w=0&k=20&c=cd8e6RBGOe4b8a8vTcKW0Jo9JONv1bKSMTKcxaCra8c="
)

private val IMAGES = listOf(
    "https://www.dndspeak.com/wp-content/uploads/2021/03/Landscapes-1.jpg",
    "https://i.etsystatic.com/13900895/r/il/d88470/4982312733/il_fullxfull.4982312733_nmb7.jpg",
    "https://i.ytimg.com/vi/my8p4pZ_FIk/maxresdefault.jpg",
    "https://images.nightcafe.studio/jobs/U1lMCCZWHTbRZfeKkke7/U1lMCCZWHTbRZfeKkke7--1--hsd0o_2x.jpg",
    "https://i.pinimg.com/originals/a0/39/1b/a0391b038aa65ac3261079211ed030eb.jpg",
    "https://i.pinimg.com/originals/a0/39/1b/a0391b038aa65ac3261079211ed030eb.jpg",
    "https://i.pinimg.com/originals/a0/39/1b/a0391b038aa65ac3261079211ed030b"
)

private val TEMP_POST1 = Post(
    id = "eGICdxTmM49V90p2o7lF",
    createDate = Date(),
    user = TEMP_USER,
    title = "Random God UPDATED!",
    text = "tetete",
    media = IMAGES
)


@Composable
fun LibraryPage() {
    val resources = loadDrawables(R.drawable::class.java)
    val scrollState = rememberScrollState()
    Scaffold (
        containerColor = SDTheme.colors.bgPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(it)
                .padding(start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            AvatarsTest()

            OutlinedContainer(
                parentWidth = true,
                paddingHorizontal = 16.dp,
                paddingVertical = 16.dp
            ) {
                IconsGrid(resources)
            }

            SwitchTest()
            ChipsTest()
            TextFieldsTest()
            ButtonsTest()
            TilesTest()

            WikiTile(type = WikiTypeRes.CHARACTER)

            TestInfoBottomSheet(resources)
            ModalBottomSheetNullTest()
            ModalBottomSheetTest()
            GalleryBlockTest()
        }
    }

}

@Composable
private fun AvatarsTest() {
    OutlinedContainer(
        parentWidth = true,
        paddingHorizontal = 16.dp,
        paddingVertical = 16.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Avatar",
                style = MaterialTheme.typography.titleMedium
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Avatar(imgUrl = TEMP_USER.imageUrl, char = TEMP_USER.name!![0], size = 96.dp)
                Avatar(char = 'A', size = 64.dp)
                Avatar(char = 'B', size = 44.dp, inactive = false)
                Avatar(imgUrl = TEMP_USER.imageUrl, char = TEMP_USER.name!![0], size = 40.dp, inactive = true)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IconsGrid(resources: List<Pair<String, Int>>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Icons",
            style = MaterialTheme.typography.titleMedium
        )
        LazyVerticalGrid (
            modifier = Modifier.heightIn(max = 1000.dp),
            columns = GridCells.Fixed(6),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(resources) { res ->
                TooltipBox(
                    tooltip = { Text(
                        text = res.first,
                        modifier = Modifier
                            .clip(shape = SDTheme.shapes.corners)
                            .background(SDTheme.colors.bgSecondary)
                            .padding(4.dp)
                    ) },
                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                    state = rememberTooltipState()
                ) {
                    Icon(
                        painterResource(res.second),
                        res.first,
                        modifier = Modifier.size(36.dp)
                    )
                }

            }

        }

    }
}

@Composable
private fun SwitchTest() {

    OutlinedContainer(
        parentWidth = true,
        paddingHorizontal = 16.dp,
        paddingVertical = 16.dp
    ) {
        Row {
            CSwitch()
            CSwitch(enabled = false)
        }
    }

}

@Composable
private fun ChipsTest() {


    val items = listOf("Female", "Male", "Other")
    val selected = remember { mutableStateOf("Male") }

    OutlinedContainer(
        parentWidth = true,
        paddingHorizontal = 16.dp,
        paddingVertical = 16.dp
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ChipButton("Female", false, modifier = Modifier.weight(1f))
                ChipButton("Male", true, modifier = Modifier.weight(1f))
            }

            ChipGroup(
                items = items,
                selected = selected.value,
                onItemChanged = { selected.value = it }
            )
        }
    }

}

@Composable
private fun TextFieldsTest() {

    val value1 = remember { mutableStateOf("") }
    val value2 = remember { mutableStateOf("") }

    OutlinedContainer(
        parentWidth = true,
        paddingHorizontal = 16.dp,
        paddingVertical = 16.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RoundedTextField(value = value1.value) {
                value1.value = it
            }

            RoundedTextField(
                value = value1.value,
                hint = "Value1 2.0"
            ) {
                value1.value = it
            }

            RoundedTextField(
                value = value2.value,
                hint = "Value2",
                required = true
            ) {
                value2.value = it
            }

        }
    }

}

@Composable
private fun ButtonsTest() {
    val ctx = LocalContext.current
    OutlinedContainer(
        parentWidth = true,
        paddingHorizontal = 16.dp,
        paddingVertical = 16.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Buttons",
                style = MaterialTheme.typography.titleMedium
            )
            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CButton(label = "Click me!", onClick = { })
                CButton(label = "Click me!", enabled = false, onClick = { })
            }
            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CTonalButton(label = "Click me!", onClick = { })
                CTonalButton(label = "Click me!", enabled = false, onClick = { })
            }
            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                COutlinedButton(label = "Click me!", onClick = { })
                COutlinedButton(label = "Click me!", enabled = false, onClick = { })
            }
            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CTextButton(label = "Click me!!", onClick = {
                    FirestoreClient.getInstance().updateData(
                        FsPath.POSTS,
                        TEMP_POST1.copy(text = generateRandomString(Random.nextInt(21)), updateDate = Date()),
                        onSuccess = {
                            Toast.makeText(ctx, "Post ${TEMP_POST1.id} updated", Toast.LENGTH_SHORT).show()
                        },
                        onError = {
                            Toast.makeText(ctx, "Post ${TEMP_POST1.id} - error while updating ", Toast.LENGTH_SHORT).show()
                        }
                    )

                })
                CTextButton(label = "Click me!", enabled = false, onClick = { })
            }
            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CIconButton(iconId = R.drawable.ic_bell, onClick = { })
                CIconButton(iconId = R.drawable.ic_bell, enabled = false,  contentColor = SDTheme.colors.bgActionPrimary, onClick = { })
                CIconButtonOutlined(iconId = R.drawable.ic_bell, onClick = { })
                CIconButtonOutlined(iconId = R.drawable.ic_bell, borderColor = SDTheme.colors.bgActionPrimary, onClick = { })
                CIconButtonOutlined(iconId = R.drawable.ic_bell, contentColor = SDTheme.colors.bgActionPrimary, onClick = { })
                CIconButtonOutlined(iconId = R.drawable.ic_bell, onClick = { })
                CIconButtonOutlined(iconId = R.drawable.ic_bell, bgColor = SDTheme.colors.bgFab, contentColor = SDTheme.colors.fgOnColor, onClick = { })
            }

        }
    }
}

@Composable
private fun TilesTest() {
    OutlinedContainer(
        parentWidth = true,
        paddingHorizontal = 16.dp,
        paddingVertical = 16.dp
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArrowTile(title = "Tile 1", iconId = null)
            ArrowTile(title = "Tile 2", description = "Description", icon = null)
            ArrowTile(title = "Tile 3", iconId = R.drawable.ic_user_edit)
            ArrowTile(
                title = "Tile 4",
                description = "Description",
                icon = {
                    Image(
                        painterResource(R.drawable.book),
                        null,
                        modifier = Modifier
                            .size(40.dp)
                            .paint(
                                painter = painterResource(R.drawable.bg_image_3),
                                colorFilter = ColorFilter.tint(colorResource(R.color.avatar_blue)),
                                alpha = 0.3f
                            )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestInfoBottomSheet(resources: List<Pair<String, Int>>) {
    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ArrowTile(
        title = "InfoBottomSheet Test",
        description = "Click to show",
        iconId = R.drawable.ic_calendar,
        onClick = {
            coroutineScope.launch {
                isBottomSheetVisible = true
                sheetState.expand()
            }
        }
    )

    CBottomSheet(
        title = "Icons",
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = { coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion { isBottomSheetVisible = false }
        }
    ) {
        IconsGrid(resources)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheetNullTest() {

    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ArrowTile(
        title = "ModalBottomSheet. New post",
        description = "Click to show. Post = null",
        iconId = R.drawable.ic_calendar,
        onClick = {
            coroutineScope.launch {
                isBottomSheetVisible = true
                sheetState.expand()
            }
        }
    )

    PostEditorSheet(
        isCreateMode = true,
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = { coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion { isBottomSheetVisible = false }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalBottomSheetTest() {

    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ArrowTile(
        title = "ModalBottomSheet. Edit post",
        description = "Click to show. Post = TEMP_POST1",
        iconId = R.drawable.ic_calendar,
        onClick = {
            coroutineScope.launch {
                isBottomSheetVisible = true
                sheetState.expand()
            }

        }
    )

    PostEditorSheet(
        post = TEMP_POST1,
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = { coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion { isBottomSheetVisible = false }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryBlockTest() {

    val coroutineScope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ArrowTile(
        title = "GalleryBlock Test",
        description = "Click to show",
        iconId = R.drawable.ic_calendar,
        onClick = {
            coroutineScope.launch {
                isBottomSheetVisible = true
                sheetState.expand()
            }
        }
    )

    CBottomSheet(
        title = "Gallery Blocks",
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismissRequest = { coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion { isBottomSheetVisible = false }
        }
    ) {

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text("Images count: ${IMAGES.subList(0, 1).size}")
            GalleryBlock(IMAGES.subList(0, 1))

            Text("Images count: ${IMAGES.subList(0, 2).size}")
            GalleryBlock(IMAGES.subList(0, 2))

            Text("Images count: ${IMAGES.subList(0, 3).size}")
            GalleryBlock(IMAGES.subList(0, 3))

            Text("Images count: ${IMAGES.subList(0, 4).size}")
            GalleryBlock(IMAGES.subList(0, 4))

            Text("Images count: ${IMAGES.size}")
            GalleryBlock(IMAGES)
        }


    }

}


private fun loadDrawables(clz: Class<*>): List<Pair<String, Int>> {
    val res = mutableListOf<Pair<String, Int>>()
    val fields: Array<Field> = clz.declaredFields
    for (field in fields) {
        val drawableId: Int
        try {
            drawableId = field.getInt(clz)
            res.add(Pair(field.name, drawableId))
        } catch (e: Exception) {
            continue
        }
    }
    return res
}

private fun generateRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length).map { allowedChars.random() }.joinToString("")
}
