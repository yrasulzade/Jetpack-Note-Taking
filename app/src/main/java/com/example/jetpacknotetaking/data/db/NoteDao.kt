package com.example.jetpacknotetaking.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpacknotetaking.data.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllUsers(): Flow<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM notes WHERE id=:noteId")
    fun getNote(noteId: Long): Flow<Note?>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("UPDATE notes SET title = :title, note = :note WHERE id = :noteId")
    suspend fun updateItemById(title: String, note: String, noteId: Long)

}
