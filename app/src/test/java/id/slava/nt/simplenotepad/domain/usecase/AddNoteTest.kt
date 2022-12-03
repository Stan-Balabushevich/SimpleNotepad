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

    @Before
    fun setUp() {

        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)

    }

    @Test
    fun `Empty title error`() = runBlocking {

        val note = Note(title = "", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 3)

        try {
            addNote.invoke(note)
        } catch (e: InvalidNoteException){

            assertThat(e.message).isEqualTo("Title can't be empty")

        }
    }

    @Test
    fun `Null note error`() = runBlocking {

        val note = null

        try {
            addNote.invoke(note)
        } catch (e: InvalidNoteException){

            assertThat(e.message).isEqualTo("Note does not exist")

        }
    }

    @Test
    fun `Note should not be null when adding`() = runBlocking {

        val testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 3)

//        val testNote: Note? = null

        try {

            addNote.invoke(testNote)

        }catch (e: InvalidNoteException){

            assertThat(testNote).isNotNull()
        }
    }

    @Test
    fun `Note title should not be empty when adding`() = runBlocking {

        val testNote = Note(title = "Title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 3)

        try {

            addNote.invoke(testNote)

        }catch (e: InvalidNoteException){

            assertThat(testNote.title).isNotEqualTo("")
        }
    }

}
