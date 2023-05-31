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
    private lateinit var searchTitle: SearchTitle

    @Before
    fun setUp() {

        fakeNoteRepository = FakeNoteRepository()
        searchTitle = SearchTitle(fakeNoteRepository)
        val testList = listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5),
            Note(title = "dhyrj", content = "jmpmk", dateCreated = 12L, dateEdited = 21L, id = 5),
            Note(title = "eryuui", content = "orykujtyk", dateCreated = 12L, dateEdited = 21L, id = 5))

         runBlocking { testList.forEach {
             fakeNoteRepository.insertNote(it)
            }
         }

    }

    @Test
    fun `Check if search by title correct`() = runBlocking {

        val testTitle = "title"

        val expected = searchTitle.invoke(testTitle)
        val actual =  listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5))

        expected.test {
            val emission = awaitItem()
            Truth.assertThat(actual).isEqualTo(emission)
            cancelAndIgnoreRemainingEvents()
        }
    }
}