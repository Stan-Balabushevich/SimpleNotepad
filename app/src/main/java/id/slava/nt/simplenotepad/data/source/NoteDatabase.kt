package id.slava.nt.simplenotepad.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import id.slava.nt.simplenotepad.domain.models.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}