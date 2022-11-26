package id.slava.nt.simplenotepad.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchContentTest {

    private lateinit var fakeNoteRepository: NoteRepository
    private lateinit var testNote: Note
    private lateinit var searchContent: SearchContent
    private lateinit var addNote: AddNote

    @Before
    fun setUp() {

        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNote(fakeNoteRepository)
        searchContent = SearchContent(fakeNoteRepository)
        testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5)

        runBlocking {  addNote.invoke(testNote) }

    }

    @Test
    fun `Check if search by content`() = runBlocking{

        val testContent = "test content"
        val expected = searchContent.invoke(testContent)
        val actual =  listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5))

        expected.test {
            val emission = awaitItem()
            Truth.assertThat(actual).isEqualTo(emission)
            cancelAndIgnoreRemainingEvents()
        }
    }
}