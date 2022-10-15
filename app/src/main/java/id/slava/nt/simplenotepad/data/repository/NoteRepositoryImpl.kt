package id.slava.nt.simplenotepad.data.repository

import id.slava.nt.simplenotepad.data.source.NoteDao
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun getSearchTitleFlow(searchText: String): Flow<List<Note>> =
        dao.getSearchTitleFlow(searchText)


    override suspend fun getSearchContentFlow(searchText: String): Flow<List<Note>> =
        dao.getSearchContentFlow(searchText)

    override fun deleteAll() {
        dao.deleteAll()
    }
}