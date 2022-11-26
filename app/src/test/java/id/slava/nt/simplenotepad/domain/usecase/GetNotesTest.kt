package id.slava.nt.simplenotepad.domain.usecase

import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var fakeNoteRepository: NoteRepository
    private lateinit var getNotes: GetNotes

    @Before
    fun setUp() {

        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    dateEdited = index.toLong(),
                    dateCreated = index.toLong()
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeNoteRepository.insertNote(it) }
        }

    }



    @Test
    fun `order notes by title, correct order`() = runBlocking{

        val notes = getNotes(NoteOrder.Title).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `order notes by date created, correct order`() = runBlocking{

        val notes = getNotes(NoteOrder.DateCreated).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].dateCreated).isGreaterThan(notes[i+1].dateCreated)
        }
    }

    @Test
    fun `order notes by date edited, correct order`() = runBlocking{

        val notes = getNotes(NoteOrder.DateEdited).first()

        for(i in 0..notes.size - 2) {
            assertThat(notes[i].dateEdited).isGreaterThan(notes[i+1].dateEdited)
        }
    }


}