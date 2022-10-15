package id.slava.nt.simplenotepad.domain.repository

import id.slava.nt.simplenotepad.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getSearchTitleFlow(searchText: String): Flow<List<Note>>

    suspend fun getSearchContentFlow(searchText: String): Flow<List<Note>>

    fun deleteAll()
}