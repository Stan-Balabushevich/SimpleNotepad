package id.slava.nt.simplenotepad.di

import id.slava.nt.simplenotepad.presentation.add_edit_note.AddEditNoteViewModel
import id.slava.nt.simplenotepad.presentation.list_notes.NotesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        NotesListViewModel(noteUseCases = get())
    }

    viewModel {
        AddEditNoteViewModel(noteUseCases = get(), savedStateHandle = get())
    }
}