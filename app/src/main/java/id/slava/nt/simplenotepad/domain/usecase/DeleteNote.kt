package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.domain.models.InvalidNoteException
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note?) {
        if (note == null){

            throw InvalidNoteException("Note does not exist")

        } else{
            repository.deleteNote(note)
        }

    }
}