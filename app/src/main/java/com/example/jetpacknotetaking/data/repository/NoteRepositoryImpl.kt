package com.example.jetpacknotetaking.data.repository

import com.example.jetpacknotetaking.data.db.NoteDao
import com.example.jetpacknotetaking.data.entity.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllUsers(): Flow<List<Note>> {
        return noteDao.getAllUsers()
    }

}
