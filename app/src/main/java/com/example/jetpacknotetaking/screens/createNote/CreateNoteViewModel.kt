package com.example.jetpacknotetaking.screens.createNote

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacknotetaking.App
import com.example.jetpacknotetaking.data.db.NoteDao
import com.example.jetpacknotetaking.data.entity.Note
import com.example.jetpacknotetaking.data.repository.NoteRepository
import com.example.jetpacknotetaking.util.ifNotNull
import com.example.jetpacknotetaking.util.ifNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(private val userDao: NoteDao) : ViewModel() {
    private val _note: MutableStateFlow<NoteState> = MutableStateFlow(NoteState(null))
    val note: StateFlow<NoteState?> get() = _note
    private var noteId: Long? = null

    init {
        Log.d("hey22", ": ")
    }

    fun createOrUpdateNote(title: String, note: String) {
        viewModelScope.launch {

            Log.d("8484", "createOrUpdateNote: $noteId")
            noteId.ifNotNull {
                userDao.updateItemById(title = title, note = note, noteId = it)
            }
            noteId.ifNull {
                userDao.insertNote(Note(title = title, note = note))
            }
        }
    }

    fun getNote(noteId: Long): Flow<Note?> {
        Log.d("7878", "getNote: $noteId")
        if (noteId != -1L) {
            this.noteId = noteId

            return userDao.getNote(noteId)
        }
        return emptyFlow()
    }

    fun checkIfTextFieldsChanged(
        note: Note?,
        titleState: MutableState<TextFieldValue>,
        noteTextFieldState: MutableState<TextFieldValue>
    ): Boolean {
        val res = if (note != null) {
            note.title != titleState.value.text || note.note != noteTextFieldState.value.text
        } else {
            titleState.value.text.isNotEmpty() || noteTextFieldState.value.text.isNotEmpty()
        }
        Log.d("9384834", "checkIfTextFieldsChanged: $res")

        return res
    }
}


data class NoteState(val note: Note?)
