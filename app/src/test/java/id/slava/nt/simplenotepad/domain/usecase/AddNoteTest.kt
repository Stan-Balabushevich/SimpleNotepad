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
    private lateinit var fullNote: Note
    private lateinit var emptyTitleNote: Note
    private lateinit var emptyContentNote: Note

    @Before
    fun setUp() {

        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)

        emptyTitleNote = Note(title = "", content = "sfgrfg", dateCreated = 12L, dateEdited = 21L)

        emptyContentNote = Note(title = "sfgrfg", content = "", dateCreated = 12L, dateEdited = 21L)

        fullNote = Note(title = "sfgrfg", content = "cghjsxtfujh", dateCreated = 12L, dateEdited = 21L, id = 3)




    }

    @Test
    fun `Note added correctly`() = runBlocking {
        addNote(fullNote)

        val noteAdded = fakeRepository.getNoteById(fullNote.id!!)

        assertThat(noteAdded).isEqualTo(fullNote)

    }

    @Test
    fun `Title is not empty`() = runBlocking {

        try {
            addNote(emptyTitleNote)

        } catch(ex: InvalidNoteException) {
            assertThat(ex.message).isEqualTo("The title of the note can't be empty.")
        }
    }
}