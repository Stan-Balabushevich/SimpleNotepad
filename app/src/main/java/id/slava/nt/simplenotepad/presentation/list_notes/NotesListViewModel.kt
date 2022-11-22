package id.slava.nt.simplenotepad.presentation.list_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

const val TITLE = "Title"
const val CONTENT = "Content"
const val TIME_ADDED = "Added"
const val TIME_EDITED = "Edited"

class NotesListViewModel(private val noteUseCases: NoteUseCases): ViewModel() {

    private var getNotesJob: Job? = null

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val order = NoteOrder.DateCreated(OrderType.Ascending)

    val title = "AdFJOIirTZW".toList()


    init {

//        val notesTest = List(10) { Note( id = it,
//            title = "${title.shuffled().joinToString("")} $it",
//            content = ("Composem ipsum color sit lazy, " +
//                    "padding theme elit, sed do bouncy. ")
//                .repeat(4), dateCreated = it.toLong(), dateEdited = it.toLong() ) }
//
//        val notes = mutableListOf<Note>()
//
//
//        notesTest.forEach { note ->
//            viewModelScope.launch {
//                noteUseCases.addNote(note)
//            }
//        }

        getNotes(NoteOrder.DateCreated(OrderType.Ascending))

    }


    private val _searchBy = MutableStateFlow("")
//    val searchBy: StateFlow<String>
//        get() = _searchBy

    fun setSearchBy(searchBy: String){
        _searchBy.value = searchBy
    }

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    fun setSearchText(searchText: String){
        when(_searchBy.value){
            TITLE -> searchByTitle(searchText,order)
            CONTENT -> searchByContent(searchText,order)
            "" -> getNotes(order)

        }
    }
    private  fun searchByTitle(text: String, noteOrder: NoteOrder){

        noteUseCases.searchTitle(text)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)

    }

    private  fun searchByContent(text: String, noteOrder: NoteOrder){

        noteUseCases.searchContent(text)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)

    }

    private fun getNotes(noteOrder: NoteOrder) {
        // what the job for?
//        getNotesJob?.cancel()
//        getNotesJob =
            noteUseCases.getNotes()
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

    fun millisToDate(seconds: Long): String
            = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ROOT).format(seconds)



}