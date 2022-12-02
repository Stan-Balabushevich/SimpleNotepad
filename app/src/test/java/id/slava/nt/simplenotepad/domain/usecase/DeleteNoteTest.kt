package id.slava.nt.simplenotepad.domain.usecase

import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.InvalidNoteException
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DeleteNoteTest {

    private lateinit var fakeNoteRepository: NoteRepository
    private lateinit var deleteNote: DeleteNote

    @Before
    fun setUp() {

        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)

    }

    @Test
    fun `Null note exception`() = runBlocking{

        val note: Note? = null

        try {
            deleteNote.invoke(note = note)
        } catch (e: InvalidNoteException){
            assertThat(e.message).isEqualTo("Note does not exist")
        }

    }

    @Test
    fun `Note should not be null when deleting`() = runBlocking {

        val testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 3)

//        val testNote: Note? = null

        try {

            deleteNote.invoke(note = testNote)

        }catch (e: InvalidNoteException){

            assertThat(testNote).isNotNull()
        }
    }
}