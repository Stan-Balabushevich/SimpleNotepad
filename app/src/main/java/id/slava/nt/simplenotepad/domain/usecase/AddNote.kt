package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.domain.models.InvalidNoteException
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note?) {

        if (note == null){
            throw InvalidNoteException("Note does not exist")
        }
        if(note.title.isBlank()) {
            throw InvalidNoteException("Title can't be empty")
        }
        repository.insertNote(note)
    }
}
