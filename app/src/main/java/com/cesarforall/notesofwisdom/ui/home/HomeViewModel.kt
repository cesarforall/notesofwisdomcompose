package com.cesarforall.notesofwisdom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(notesRepository: NotesRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        notesRepository.gellAllNotesStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val notesList: List<Note> = listOf()
)