package id.slava.nt.simplenotepad.presentation.list_notes

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.slava.nt.simplenotepad.BuildConfig
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.SearchBy
import id.slava.nt.simplenotepad.domain.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NotesListViewModel(private val noteUseCases: NoteUseCases): ViewModel() {

//    private var getNotesJob: Job? = null

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



    private fun writeTextTofile(context: Context, text: String){

            val path = context.filesDir
            File(path,"notes_list_simplenotepad.txt").writeText(text)

    }

    private fun shareNotesBuilder(noteList: List<Note>): String{

        val builder = StringBuilder()

        noteList.forEach {  note ->
            builder
                .appendLine(note.title)
                .appendLine(note.content)
                .appendLine()
        }
        return builder.toString()
    }

    fun shareFile(context: Context){

        try{

            writeTextTofile(context,shareNotesBuilder(state.value.notes))

        }catch (e: Exception){

            viewModelScope.launch {

                errorChannel.send(UiText.StringResource(R.string.save_file_error))
            }

            return
        }

        val path = context.filesDir
        val file = File(path, "notes_list_simplenotepad.txt")

        if (file.exists()) {
            val uri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_SUBJECT, "List of Notes from SimpleNotepad")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else{

            viewModelScope.launch {

                errorChannel.send(UiText.StringResource(R.string.save_file_error))
            }

        }

    }

}