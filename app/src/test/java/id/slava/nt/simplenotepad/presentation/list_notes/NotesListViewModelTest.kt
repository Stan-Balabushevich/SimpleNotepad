package id.slava.nt.simplenotepad.presentation.list_notes

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
            Note(title = "dhyrj", content = "jmpmk", dateCreated = 12L, dateEdited = 21L, id = 2),
            Note(title = "eryuui", content = "orykujtyk", dateCreated = 12L, dateEdited = 21L, id = 3))

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

//    @Test
//    fun `fun setSearchText called with text`(){
//
//        fakeViewModel.setSearchText("gfjfyhj")
//
//        val actual = fakeViewModel.state.value.notes
//        val expected =  listOf(Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5),
//            Note(title = "dhyrj", content = "jmpmk", dateCreated = 12L, dateEdited = 21L, id = 5),
//            Note(title = "eryuui", content = "orykujtyk", dateCreated = 12L, dateEdited = 21L, id = 5))
//
//        assertThat(actual).isEqualTo(expected)
//
//
//
//    }
}