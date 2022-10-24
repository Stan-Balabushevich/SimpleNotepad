package id.slava.nt.simplenotepad.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.slava.nt.simplenotepad.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM database_notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM database_notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from database_notes where title like '%' || :searchText || '%' ")
    fun getSearchTitleFlow(searchText: String): Flow<List<Note>>

    @Query("select * from database_notes where content like '%' || :searchText || '%' ")
    fun getSearchContentFlow(searchText: String): Flow<List<Note>>

    @Query("delete from database_notes")
    fun deleteAll()
}
