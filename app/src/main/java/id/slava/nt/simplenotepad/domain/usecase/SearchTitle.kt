package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.domain.repository.NoteRepository

class SearchTitle(
    private val repository: NoteRepository
) {

     operator fun invoke(text: String) =
        repository.getSearchTitle(text)


}