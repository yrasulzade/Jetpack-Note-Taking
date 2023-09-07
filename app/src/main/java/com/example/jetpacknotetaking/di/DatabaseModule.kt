package com.example.jetpacknotetaking.di

import android.content.Context
import androidx.room.Room
import com.example.jetpacknotetaking.data.db.AppDatabase
import com.example.jetpacknotetaking.data.db.NoteDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "my-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChannelDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.userDao()
    }
}
