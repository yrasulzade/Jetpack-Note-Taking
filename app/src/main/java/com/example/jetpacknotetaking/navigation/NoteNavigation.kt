package com.example.jetpacknotetaking.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.jetpacknotetaking.screens.createNote.CreateNote
import com.example.jetpacknotetaking.screens.home.Home
import com.example.navigation.AppComposeNavigator
import com.example.navigation.NoteScreens
import com.example.navigation.NoteScreens.Companion.argument_note_id

fun NavGraphBuilder.noteNavigation(
    composeNavigator: AppComposeNavigator
) {

    composable(route = NoteScreens.Home.name) {
        Home(composeNavigator = composeNavigator)
    }

    composable(
        route = NoteScreens.CreateNote.name,
        arguments = NoteScreens.CreateNote.navArguments
    ) {
        val noteId = it.arguments?.getLong(argument_note_id) ?: -1L
        CreateNote(composeNavigator = composeNavigator, noteId = noteId)
    }
}
