package com.cesarforall.notesofwisdom.data

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun gellAllNotesStream() : Flow<List<Note>>

    fun getNoteStream(id: Int): Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}