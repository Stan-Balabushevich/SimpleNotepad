package id.slava.nt.simplenotepad.data.repository

import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepositoryTest: NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>>  = flow { emit(notes) }

    override suspend fun getNoteById(id: Int): Note? = notes.find { it.id == id }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override fun getSearchTitle(searchText: String): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override fun getSearchContent(searchText: String): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        notes.clear()
    }


}