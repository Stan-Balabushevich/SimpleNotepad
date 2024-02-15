package id.slava.nt.simplenotepad.presentation.list_notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.slava.nt.simplenotepad.common.shareFileCommon
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.SearchBy
import id.slava.nt.simplenotepad.domain.util.UiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NotesListViewModel(private val noteUseCases: NoteUseCases): ViewModel() {

    // Job allows you to cancel coroutine which is your network or database request for example,
    // and it is taking too long or you want to relaunch it
    private var getNotesJob: Job? = null

//    private val _state = mutableStateOf(NotesState())
//    val state: State<NotesState> = _state

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private val errorChannel = Channel<UiText>()
    val errors = errorChannel.receiveAsFlow()

    init {

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
//    private val _searchBy = mutableStateOf(SearchBy.TITLE)

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
        // Job allows you to cancel coroutine which is your network or database request for example,
        // and it is taking too long or you want to relaunch it
        getNotesJob?.cancel()
        getNotesJob =
            noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

    fun shareTxtFile(context: Context){

        viewModelScope.launch {
            errorChannel.send(shareFileCommon(context,state.value.notes ))
        }

    }

}