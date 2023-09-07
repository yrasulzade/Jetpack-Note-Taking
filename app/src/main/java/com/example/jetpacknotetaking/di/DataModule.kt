package com.example.jetpacknotetaking.di

import com.example.jetpacknotetaking.data.repository.NoteRepository
import com.example.jetpacknotetaking.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository
}
