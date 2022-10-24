package id.slava.nt.simplenotepad.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "database_notes")
@Parcelize
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val dateCreated: Long,
    val dateEdited: Long
): Parcelable

class InvalidNoteException(message: String): Exception(message)