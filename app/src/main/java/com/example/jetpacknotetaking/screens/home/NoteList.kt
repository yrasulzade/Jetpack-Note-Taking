package com.example.jetpacknotetaking.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpacknotetaking.R
import com.example.jetpacknotetaking.data.entity.Note
import com.example.jetpacknotetaking.ui.theme.Color25
import com.example.jetpacknotetaking.ui.theme.Pink40
import com.example.jetpacknotetaking.ui.theme.Pink80
import com.example.jetpacknotetaking.ui.theme.Purple
import com.example.jetpacknotetaking.ui.theme.RusticRed
import com.example.navigation.AppComposeNavigator
import com.example.navigation.NoteScreens

private const val NO_ID = -1L

@Composable
fun Home(composeNavigator: AppComposeNavigator, viewModel: HomeViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier
            .background(color = Color25)
            .fillMaxSize()
    ) {
        val noteList by viewModel.orderLines.collectAsStateWithLifecycle(listOf())
        var isSearchFocused by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        val emptyNoteMessage = if (searchQuery.isNotEmpty()) {
            stringResource(id = R.string.search_result_not_found)
        } else {
            stringResource(id = R.string.create_note)
        }

        Column {
            AnimatedVisibility(visible = (!noteList.isNullOrEmpty()) || (noteList.isNullOrEmpty() && searchQuery.isNotEmpty())) {
                NoteSearchBox(
                    onSearchClicked = {
                        searchQuery = it
                        viewModel.searchNotes(it)
                    },
                    onFocusChange = { isSearchFocused = it })
            }


            if (!noteList.isNullOrEmpty()) {
                NotesHeader(composeNavigator, noteList.orEmpty(), viewModel)
            } else {
                EmptyNoteView(emptyNoteMessage)
            }
        }

        FloatingActionButton(
            onClick = {
                composeNavigator.navigate(NoteScreens.CreateNote.createRoute(NO_ID))
            },
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp)
                .shadow(16.dp)
                .clip(CircleShape),
            containerColor = Color25
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add),
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteSearchBox(onSearchClicked: (String) -> Unit, onFocusChange: (Boolean) -> Unit) {
    val query = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)

    OutlinedTextField(
        value = query.value,
        textStyle = TextStyle(color = RusticRed, fontSize = 20.sp),
        onValueChange = {
            query.value = it
            onSearchClicked.invoke(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Pink80,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
        placeholder = {
            Text(
                stringResource(id = R.string.search), fontSize = 20.sp, color = Pink40
            )
        },
        leadingIcon = {
            Icon(
                tint = Color25,
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = query.value.isNotEmpty()) {
                Icon(
                    tint = Color25,
                    painter = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_cancel),
                    contentDescription = stringResource(id = R.string.cd_cancel),
                    modifier = Modifier.clickable {
                        onSearchClicked.invoke("")
                        focusManager.clearFocus()
                        query.value = ""
                    }
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
//            onSearchClicked.invoke(query.value)
        }),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.onFocusChanged { onFocusChange.invoke(it.isFocused) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesHeader(
    composeNavigator: AppComposeNavigator,
    noteList: List<Note>,
    viewModel: HomeViewModel
) {
    Column {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),

            modifier = Modifier
                .animateContentSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {

            items(noteList, key = { cartRow -> cartRow.id },
                itemContent = { noteItem ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it.ordinal == 1 || it.ordinal == 2) {
                                viewModel.deleteNote(noteItem)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            dismissState.dismissDirection ?: return@SwipeToDismiss
                            DismissItemBackground()
                        },
                        dismissContent = {
                            NoteItem(noteItem.title) {
                                composeNavigator.navigate(NoteScreens.CreateNote.createRoute(noteItem.id))
                            }
                        },
                        dismissThresholds = {
                            FractionalThreshold(1f)
                        }
                    )
                })
        }
    }
}

@Composable
@Preview(widthDp = 400, heightDp = 70)
fun DismissItemBackground() {
    Box(
        modifier = Modifier
            .background(
                color = Color.Red,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_delete_sweep_24),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(48.dp)
                .align(
                    Alignment.CenterEnd
                )
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun NoteItem(item: String, onNoteItemCLick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = Purple, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable {
                onNoteItemCLick.invoke()
            }
    ) {
        Text(
            text = item,
            modifier = Modifier.padding(16.dp),
            fontSize = 25.sp,
        )
    }
}

@Composable
fun EmptyNoteView(emptyNoteMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.empty_notes), contentDescription = null)
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            text = emptyNoteMessage,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview("default")
@Composable
fun ProfilePreview() {
    NoteItem("Title") {}
}
