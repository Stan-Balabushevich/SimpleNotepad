package id.slava.nt.simplenotepad.presentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
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
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.domain.models.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddEditNoteViewModelTest {

    private lateinit var fakeUseCases: NoteUseCases
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var fakeViewModel: AddEditNoteViewModel
    private lateinit var fakeSavedStateHandle: SavedStateHandle
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        fakeNoteRepository = FakeNoteRepository()
        fakeSavedStateHandle = SavedStateHandle().apply {
            set("noteId",3)
        }
        fakeUseCases = NoteUseCases( getNotes = GetNotes(fakeNoteRepository), deleteNote = DeleteNote(fakeNoteRepository),
            deleteAllNotes = DeleteAllNotes(fakeNoteRepository), addNote = AddNote(fakeNoteRepository), getNote = GetNote(fakeNoteRepository),
            searchTitle = SearchTitle(fakeNoteRepository), searchContent = SearchContent(fakeNoteRepository)
        )
        fakeViewModel = AddEditNoteViewModel(fakeSavedStateHandle,fakeUseCases)

        val testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 3)

        runBlocking { fakeUseCases.addNote.invoke(note = testNote) }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun`set title value correctly`(){

        fakeViewModel.setTitleValue(NoteTextFieldState(text = "Title"))

        val actual = fakeViewModel.noteTitle.value.text
        val expected = "Title"

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun`set content value correctly`(){

        fakeViewModel.setContentValue(NoteTextFieldState(text = "Content"))

        val actual = fakeViewModel.noteContent.value.text
        val expected = "Content"

        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun`Getting correct note from repository by savedStateHandle note id`(){

        val actual = fakeSavedStateHandle.get<Int>("noteId")
        val expected = 3

        assertThat(actual).isEqualTo(expected)



    }
}