package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.domain.models.InvalidNoteException
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: FakeNoteRepository
    private lateinit var testNote: Note

    @Before
    fun setUp() {

        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
        testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 3)

    }

    @Test
    fun `Note added correctly`() = runBlocking {
        addNote(testNote)

        val testNoteId = 3

        val noteAdded = fakeRepository.getNoteById(testNoteId)

        assertThat(noteAdded).isEqualTo(testNote)

    }

    @Test
    fun `Catching error saving note`() = runBlocking {

        try {

            addNote.invoke(testNote)

        } catch (e: InvalidNoteException){

            assertThat(e.message).isEqualTo("Could not save note")

        }

    }

}