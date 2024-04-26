package id.slava.nt.simplenotepad.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val deleteAllNotes: DeleteAllNotes,
    val addNote: AddNote,
    val getNote: GetNote,
    val searchTitle: SearchTitle,
    val searchContent: SearchContent
)
