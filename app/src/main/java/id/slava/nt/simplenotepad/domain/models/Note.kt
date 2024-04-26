package id.slava.nt.simplenotepad.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "database_notes")
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val dateCreated: Long,
    val dateEdited: Long
)

class InvalidNoteException(message: String): Exception(message)