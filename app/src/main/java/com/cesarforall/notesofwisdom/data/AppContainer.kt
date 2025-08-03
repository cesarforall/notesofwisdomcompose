package com.cesarforall.notesofwisdom.data

import android.content.Context
import com.cesarforall.notesofwisdom.data.database.NotesDatabase

interface AppContainer {
    val notesRepository: NotesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val notesRepository: NotesRepository by lazy {
        OfflineNoteRepository(NotesDatabase.getDatabase(context).noteDao())
    }
}
