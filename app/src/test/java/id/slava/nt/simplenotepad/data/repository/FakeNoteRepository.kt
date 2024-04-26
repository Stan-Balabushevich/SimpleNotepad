package id.slava.nt.simplenotepad.data.repository

import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository: NoteRepository {

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
        val titleNotes = mutableListOf<Note>()

        notes.forEach{ note ->
            if(note.title.contains(searchText)){
                titleNotes.add(note)
            }
        }

        return flow { emit(titleNotes) }
    }

    override fun getSearchContent(searchText: String): Flow<List<Note>> {
        val contentNotes = mutableListOf<Note>()

        notes.forEach{ note ->
            if(note.content.contains(searchText)){
                contentNotes.add(note)
            }
        }

        return flow { emit(contentNotes) }
    }

    override fun deleteAll() {
        notes.clear()
    }


}