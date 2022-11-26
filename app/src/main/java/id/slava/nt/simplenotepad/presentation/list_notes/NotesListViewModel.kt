package id.slava.nt.simplenotepad.presentation.list_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.SearchBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

class NotesListViewModel(private val noteUseCases: NoteUseCases): ViewModel() {

//    private var getNotesJob: Job? = null

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

//    val title = "AdFJOIirTZW".toList()


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

        getNotes(NoteOrder.DateCreated)

    }

    fun orderBy(noteOrder: NoteOrder){
        if (state.value.noteOrder::class == noteOrder::class &&
            state.value.noteOrder == noteOrder){
            return
        }
        getNotes(noteOrder)
    }


    private val _searchBy = MutableStateFlow(SearchBy.TITLE)

    fun setSearchBy(searchBy: SearchBy){
        _searchBy.value = searchBy
    }

    fun setSearchText(searchText: String){

        if (searchText.isNotBlank()){
            when(_searchBy.value){
                SearchBy.TITLE -> searchByTitle(searchText,state.value.noteOrder)
                SearchBy.CONTENT -> searchByContent(searchText,state.value.noteOrder)
            }
        }else{
          getNotes(state.value.noteOrder)
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
            noteUseCases.getNotes(noteOrder)
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