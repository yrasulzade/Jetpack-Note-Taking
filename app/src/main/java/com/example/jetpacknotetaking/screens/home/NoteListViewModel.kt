package com.example.jetpacknotetaking.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacknotetaking.App
import com.example.jetpacknotetaking.data.db.NoteDao
import com.example.jetpacknotetaking.data.repository.NoteRepository
import com.example.jetpacknotetaking.data.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val userDao: NoteDao
) : ViewModel() {

    private val _orderLines: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    val orderLines: StateFlow<List<Note>?> get() = _orderLines

    private val notesList = mutableListOf<Note>()

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            noteRepository.getAllUsers().collectLatest {
                notesList.clear()
                notesList.addAll(it)

                _orderLines.value = it
            }
        }
    }

    fun searchNotes(searchQuery: String) {
        Log.d("geek", "searchNotes: ------------------------>>>>>>>>>>>>>> $searchQuery")

        if (searchQuery.isEmpty()) {
            fetchNotes()
        } else {
            val searchResult = notesList.filter {
                it.title.contains(searchQuery)
            }
            _orderLines.value = searchResult
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            delay(250) // show delete process fully in UI
            userDao.deleteNote(note)
        }
    }
}
