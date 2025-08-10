package com.cesarforall.notesofwisdom.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cesarforall.notesofwisdom.NotesApplication
import com.cesarforall.notesofwisdom.ui.home.HomeViewModel
import com.cesarforall.notesofwisdom.ui.noteForm.NoteFormViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NoteFormViewModel(notesApplication().container.notesRepository)
        }
        initializer {
            HomeViewModel(notesApplication().container.notesRepository)
        }
    }
}

fun CreationExtras.notesApplication(): NotesApplication = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)