package id.slava.nt.simplenotepad.presentation.add_edit_note

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.slava.nt.simplenotepad.domain.models.InvalidNoteException
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.usecase.NoteUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// if using savedHandleState no need to pass noteId as parameter
class AddEditNoteViewModel(
//                            noteId: Int,
    val savedStateHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases): ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState())
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    fun setTitleValue(title: NoteTextFieldState){
        _noteTitle.value= title
    }

    private val _noteContent = mutableStateOf(NoteTextFieldState())
    val noteContent: State<NoteTextFieldState> = _noteContent

    fun setContentValue(content: NoteTextFieldState){
        _noteContent.value = content

    }

    // shared flow uses for showing one time event like snackbar for example
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null
    private var currentNote: Note? = null
    private var originalTitle = ""
    private var originalContent = ""
    private var lastId = 0


    init {

        savedStateHandle.get<Int>("noteId")?.let { noteIdSaved ->
            if(noteIdSaved != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteIdSaved)?.also { note ->
                        currentNoteId = note.id
                        currentNote = note
                        originalTitle = note.title
                        originalContent = note.content
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        if (originalContent.isNotBlank()){
                            _noteContent.value = _noteContent.value.copy(
                                text = note.content,
                                isHintVisible = false
                            )
                        }
                    }
                }
            }
               //need to get id for new note
                else{
                viewModelScope.launch {
                    noteUseCases.getNotes.invoke().collect{
                        if(it.lastIndex != -1){
                            lastId = it[it.lastIndex].id!!
                        }
                        Log.d("AddEditNoteViewModel",lastId.toString())
                    }
                }

            }
        }
    }

    fun titleChanged(text: String){
        _noteTitle.value = noteTitle.value.copy(
            text = text
        )
    }

    fun titleFocusChanged(focusState: FocusState){
        _noteTitle.value = noteTitle.value.copy(
            isHintVisible = !focusState.isFocused &&
                    noteTitle.value.text.isBlank()
        )
    }

    fun contentChanged(text: String){

        _noteContent.value = _noteContent.value.copy(
            text = text
        )

    }

    fun contentFocusChanged(focusState: FocusState){

        _noteContent.value = _noteContent.value.copy(
            isHintVisible = !focusState.isFocused &&
                    _noteContent.value.text.isBlank()
        )

    }

    fun checkTitleAndSaveNote(defaultTitle: String){

        if (noteTitle.value.text.isBlank()) {

            try {
                if (noteContent.value.text.isNotBlank()) {
                    _noteTitle.value = _noteContent.value.copy(
                        text = _noteContent.value.text.substringBefore(" ")
                    )
                    saveNote()
                    viewModelScope.launch { _eventFlow.emit(UiEvent.ShowSuccessSnackBar) }
                } else {

                    if(defaultTitle.isNotBlank()){
                        _noteTitle.value = noteTitle.value.copy(
                            text = defaultTitle
                        )
                        saveNote()
                        viewModelScope.launch { _eventFlow.emit(UiEvent.ShowSuccessSnackBar) }

                    } else{ throw InvalidNoteException("Default title is blank") }

                }
            } catch (e: InvalidNoteException) {

                viewModelScope.launch { _eventFlow.emit(UiEvent.ShowErrorSnackBar) }

            }

        } else {
            saveNote()
            viewModelScope.launch { _eventFlow.emit(UiEvent.ShowSuccessSnackBar) }

        }

    }

    private fun saveNote(){

        if (currentNote != null) {

            val editedNote = Note(
                title = noteTitle.value.text,
                content = noteContent.value.text,
                dateCreated = currentNote!!.dateCreated,
                dateEdited = System.currentTimeMillis(),
                id = currentNoteId
            )

            currentNote = editedNote
            originalTitle = currentNote!!.title
            originalContent = currentNote!!.content

            viewModelScope.launch { noteUseCases.addNote(editedNote)}

        } else {

//                    val newNoteId = Random.nextInt(from = 0, until = 999999999)
            val newNoteId = lastId + 1

            val newNote = Note(
                title = noteTitle.value.text,
                content = noteContent.value.text,
                dateCreated = System.currentTimeMillis(),
                dateEdited = System.currentTimeMillis(),
                id = newNoteId
            )

            currentNote = newNote
            originalTitle = newNote.title
            originalContent = newNote.content
            currentNoteId = newNote.id

            viewModelScope.launch { noteUseCases.addNote(newNote) }

        }
    }

    fun checkContentAndTitleNotChanged(): Boolean = noteTitle.value.text == originalTitle && noteContent.value.text == originalContent


    fun deleteNote(){

        viewModelScope.launch {
            if ( currentNote != null){
                noteUseCases.deleteNote(currentNote!!)
            }

        }
    }

    fun shareNote(context: Context){
        context.startActivity(getShareIntent())
    }

    // Creating our Share Intent
    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, shareNoteBuilder( title = noteTitle.value.text, content= noteContent.value.text) )
        return shareIntent
    }

    private fun shareNoteBuilder(title: String, content: String): String{
        val builder = StringBuilder()
            .appendLine(title)
            .appendLine(content)

        return builder.toString()
    }

    sealed class UiEvent {
        object ShowSuccessSnackBar: UiEvent()
        object ShowErrorSnackBar: UiEvent()
    }

}