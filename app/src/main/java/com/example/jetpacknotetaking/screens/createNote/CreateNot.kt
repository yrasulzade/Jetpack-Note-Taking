package com.example.jetpacknotetaking.screens.createNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpacknotetaking.R
import com.example.jetpacknotetaking.screens.dialog.SaveConfirmationDialog
import com.example.jetpacknotetaking.ui.theme.Color25
import com.example.jetpacknotetaking.ui.theme.Color3B
import com.example.jetpacknotetaking.ui_kit.CustomOutlinedTextField
import com.example.jetpacknotetaking.ui_kit.RoundedIconButton
import com.example.navigation.AppComposeNavigator

@Composable
fun CreateNote(
    composeNavigator: AppComposeNavigator,
    noteId: Long,
    viewModel: CreateNoteViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .background(color = Color25)
            .fillMaxSize()
    ) {

        val noteState by viewModel.getNote(noteId).collectAsStateWithLifecycle(null)
        val titleState = remember { mutableStateOf(TextFieldValue()) }
        val noteTextFieldState = remember { mutableStateOf(TextFieldValue()) }
        val showConfirmationDialog = remember { mutableStateOf(false) }

        LaunchedEffect(noteState) {
            noteState?.let {
                titleState.value = TextFieldValue(noteState?.title.orEmpty())
                noteTextFieldState.value = TextFieldValue(noteState?.note.orEmpty())
            }
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val buttonModifier =
                Modifier
                    .background(color = Color3B, shape = RoundedCornerShape(12.dp))

            if (showConfirmationDialog.value) {
                SaveConfirmationDialog(
                    onSave = {
                        showConfirmationDialog.value = false
                        viewModel.createOrUpdateNote(
                            titleState.value.text,
                            noteTextFieldState.value.text
                        )
                        composeNavigator.navigateUp()
                    },
                    onDiscard = {
                        showConfirmationDialog.value = false
                        composeNavigator.navigateUp()
                    })
            }

            RoundedIconButton(
                drawable = R.drawable.ic_arrow_back,
                modifier = buttonModifier
            ) {
                composeNavigator.navigateUp()
            }
            RoundedIconButton(
                drawable = R.drawable.save,
                modifier = buttonModifier,
                isEnabled = viewModel.checkIfTextFieldsChanged(
                    noteState,
                    titleState,
                    noteTextFieldState
                )
            ) {
                showConfirmationDialog.value = true
            }
        }

        val titleInputModifier = Modifier
            .padding(vertical = 16.dp, horizontal = 4.dp)
            .fillMaxWidth()

        val noteInputModifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
            .fillMaxHeight()

        CustomOutlinedTextField(
            value = titleState.value,
            { titleState.value = it },
            placeholderText = stringResource(id = R.string.title),
            fontSize = 48.sp,
            modifier = titleInputModifier,
            shape = RoundedCornerShape(25.dp)
        )

        CustomOutlinedTextField(
            value = noteTextFieldState.value,
            { noteTextFieldState.value = it },
            placeholderText = stringResource(id = R.string.type_something),
            fontSize = 23.sp,
            modifier = noteInputModifier,
            shape = RoundedCornerShape(25.dp)
        )
    }
}
















