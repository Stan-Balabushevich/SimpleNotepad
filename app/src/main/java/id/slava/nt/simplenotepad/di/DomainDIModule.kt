package id.slava.nt.simplenotepad.di

import id.slava.nt.simplenotepad.domain.usecase.AddNote
import id.slava.nt.simplenotepad.domain.usecase.DeleteAllNotes
import id.slava.nt.simplenotepad.domain.usecase.DeleteNote
import id.slava.nt.simplenotepad.domain.usecase.GetNote
import id.slava.nt.simplenotepad.domain.usecase.GetNotes
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import id.slava.nt.simplenotepad.domain.usecase.SearchContent
import id.slava.nt.simplenotepad.domain.usecase.SearchTitle
import org.koin.dsl.module

val domainModule = module {

    factory { NoteUseCases(
        getNotes = GetNotes(repository = get()),
        deleteNote =  DeleteNote(repository = get()),
        deleteAllNotes = DeleteAllNotes(repository = get()),
        addNote = AddNote(repository = get()),
        getNote =  GetNote(repository = get()),
        searchTitle = SearchTitle(repository = get()),
        searchContent = SearchContent(repository = get())
    )  }

}