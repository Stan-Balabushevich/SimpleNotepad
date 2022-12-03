package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.domain.repository.NoteRepository

class DeleteAllNotes(
    private val repository: NoteRepository
) {

    operator fun invoke() {
        repository.deleteAll()
    }
}