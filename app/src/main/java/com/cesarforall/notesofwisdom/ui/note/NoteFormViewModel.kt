package com.cesarforall.notesofwisdom.ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.NotesRepository

class NoteFormViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateNote(note: NoteUiState) {
        noteUiState = NoteUiState(
            id = note.id,
            text = note.text,
            author = note.author,
            sourceTypeId = note.sourceTypeId,
            source = note.source,
            reference = note.reference
        )
    }

    suspend fun saveNote() {
        notesRepository.insertNote(
            Note(
                id = noteUiState.id,
                text = noteUiState.text,
                author = noteUiState.author,
                sourceTypeId = noteUiState.sourceTypeId,
                source = noteUiState.source,
                reference = noteUiState.reference
            )
        )
    }
}

data class NoteUiState(
    val id: Int = 0,
    val text: String = "",
    val author: String = "",
    val sourceTypeId: Int? = null,
    val source: String = "",
    val reference: String = ""
)