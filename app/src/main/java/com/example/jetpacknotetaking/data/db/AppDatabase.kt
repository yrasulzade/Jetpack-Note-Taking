package com.example.jetpacknotetaking.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpacknotetaking.data.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): NoteDao
}
