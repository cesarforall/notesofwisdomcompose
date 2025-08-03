package com.cesarforall.notesofwisdom.data

import com.cesarforall.notesofwisdom.data.dao.NoteDao
import kotlinx.coroutines.flow.Flow

class OfflineNoteRepository(private val noteDao: NoteDao): NotesRepository {
    override fun gellAllNotesStream(): Flow<List<Note>> = noteDao.getAll()

    override fun getNoteStream(id: Int): Flow<Note?> = noteDao.getById(id)

    override suspend fun insertNote(note: Note) = noteDao.insert(note)

    override suspend fun deleteNote(note: Note) = noteDao.delete(note)
}