package id.slava.nt.simplenotepad.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchTitleTest {

    private lateinit var fakeNoteRepository: NoteRepository
    private lateinit var testNote: Note
    private lateinit var searchTitle: SearchTitle
    private lateinit var addNote: AddNote

    @Before
    fun setUp() {

//        fakeNoteRepository = mock()
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNote(fakeNoteRepository)
        searchTitle = SearchTitle(fakeNoteRepository)
        testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5)

        runBlocking {  addNote.invoke(testNote) }

    }

    @Test
    fun `Check if search by title`() = runBlocking {

        val testTitle = "Test title"

//        Mockito.`when`(fakeNoteRepository.getSearchTitle(testNote.title)).thenReturn(flow { listOf(testNote) })

        val expected = searchTitle.invoke(testTitle)
        val actual =  listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5))

        expected.test {
            val emission = awaitItem()
            Truth.assertThat(actual).isEqualTo(emission)
            cancelAndIgnoreRemainingEvents()
        }
    }
}