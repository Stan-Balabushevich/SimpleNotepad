package id.slava.nt.simplenotepad.presentation.list_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import id.slava.nt.simplenotepad.domain.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

const val TITLE = "Title"
const val CONTENT = "Content"
const val TIME_ADDED = "Added"
const val TIME_EDITED = "Edited"

class NotesListViewModel: ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val _stateTest = mutableStateOf(emptyList<Note>())
    val stateTest: State<List<Note>> = _stateTest

//    private val _notesList = MutableStateFlow<List<Note>>(emptyList())
//    val notesList: StateFlow<List<Note>>
//        get() = _notesList

    init {
        val notesTest = List(10) { Note( id = it,
            title = "Title Composem  $it",
            content = ("Composem ipsum color sit lazy, " +
                    "padding theme elit, sed do bouncy. ")
                .repeat(4), dateCreated = it.toLong(), dateEdited = it.toLong() ) }


        _state.value = state.value.copy(
            notes = notesTest
        )

        _stateTest.value = notesTest

    }


    private val _searchBy = MutableStateFlow("")
    val searchBy: StateFlow<String>
        get() = _searchBy

    fun setSearchBy(searchBy: String){
        _searchBy.value = searchBy
    }

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    fun setSearchText(searchText: String){
        _searchText.value = searchText
    }



}