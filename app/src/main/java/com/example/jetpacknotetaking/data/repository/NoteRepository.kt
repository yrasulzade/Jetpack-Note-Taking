package com.example.jetpacknotetaking.data.repository

import com.example.jetpacknotetaking.data.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllUsers(): Flow<List<Note>>
}
