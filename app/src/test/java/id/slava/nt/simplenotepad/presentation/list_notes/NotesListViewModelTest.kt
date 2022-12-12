package id.slava.nt.simplenotepad.presentation.list_notes

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.usecase.AddNote
import id.slava.nt.simplenotepad.domain.usecase.DeleteAllNotes
import id.slava.nt.simplenotepad.domain.usecase.DeleteNote
import id.slava.nt.simplenotepad.domain.usecase.GetNote
import id.slava.nt.simplenotepad.domain.usecase.GetNotes
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import id.slava.nt.simplenotepad.domain.usecase.SearchContent
import id.slava.nt.simplenotepad.domain.usecase.SearchTitle
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.SearchBy
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class NotesListViewModelTest {

    private lateinit var fakeUseCases: NoteUseCases
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var fakeViewModel: NotesListViewModel
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        fakeNoteRepository = FakeNoteRepository()

        fakeUseCases = NoteUseCases( getNotes = GetNotes(fakeNoteRepository), deleteNote = DeleteNote(fakeNoteRepository),
            deleteAllNotes = DeleteAllNotes(fakeNoteRepository), addNote = AddNote(fakeNoteRepository), getNote = GetNote(fakeNoteRepository),
            searchTitle = SearchTitle(fakeNoteRepository), searchContent = SearchContent(fakeNoteRepository)
        )
        fakeViewModel = NotesListViewModel(fakeUseCases)

        val testList = listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 1),
            Note(title = "Test", content = "jmpmk", dateCreated = 13L, dateEdited = 22L, id = 2),
            Note(title = "eryuui", content = "orykujtyk", dateCreated = 14L, dateEdited = 23L, id = 3))

        runBlocking { testList.forEach {
            fakeNoteRepository.insertNote(it)
            }
        }


        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){

        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()

    }

    @Test
    fun `Notes state by default correct`()  {

        val expected = NotesState()
        val actual = fakeViewModel.state.value

        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun `search notes by title correct`() = runBlocking {

        fakeViewModel.setSearchBy(SearchBy.TITLE)
        fakeViewModel.setSearchText("Test")

        val actual =  fakeViewModel.state
        val expected = NotesState( notes = listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 1),
            Note(title = "Test", content = "jmpmk", dateCreated = 13L, dateEdited = 22L, id = 2)),
        noteOrder = NoteOrder.DateCreated)

        actual.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `search notes by content correct`() = runBlocking{

        fakeViewModel.setSearchBy(SearchBy.CONTENT)
        fakeViewModel.setSearchText("test")

        val actual =  fakeViewModel.state
        val expected = NotesState( notes = listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 1)),
            noteOrder = NoteOrder.DateCreated)

        actual.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `Order by has not changed`() = runBlocking{

        fakeViewModel.orderBy(NoteOrder.DateCreated)

        val actual = fakeViewModel.state
        val expected = NotesState().noteOrder

        actual.test {
            val emission = awaitItem()
            assertThat(emission.noteOrder).isEqualTo(expected)
            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `Order by has changed to title order`() = runBlocking{

        fakeViewModel.orderBy(NoteOrder.Title)

        val actual = fakeViewModel.state
        val expected = NoteOrder.Title

        actual.test {
            val emission = awaitItem()
            assertThat(emission.noteOrder).isEqualTo(expected)
            cancelAndIgnoreRemainingEvents()

        }

    }

    @Test
    fun `Order by has changed to date edited order`() = runBlocking{

        fakeViewModel.orderBy(NoteOrder.DateEdited)

        val actual = fakeViewModel.state
        val expected = NoteOrder.DateEdited

        actual.test {
            val emission = awaitItem()
            assertThat(emission.noteOrder).isEqualTo(expected)
            cancelAndIgnoreRemainingEvents()

        }

    }
}