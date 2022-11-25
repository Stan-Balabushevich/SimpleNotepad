package id.slava.nt.simplenotepad.presentation.add_edit_note

import android.content.Context
import android.content.Intent
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

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null
    private var currentNote: Note? = null
    private var originalTitle = ""
    private var originalContent = ""


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
                            _noteContent.value = _noteContent.value.copy(
                                text = note.content,
                                isHintVisible = false
                            )
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

    fun checkTitle(){

        if(noteTitle.value.text.isBlank()){

            if(noteContent.value.text.isNotBlank()){
                _noteTitle.value = _noteContent.value.copy(
                    text = _noteContent.value.text.substringBefore(" ")
                )
                saveNote()
            }else{
                _noteTitle.value = noteTitle.value.copy(
                    text = "Default Title"
                )
                saveNote()
            }

        } else{
            saveNote()
        }

    }

    private fun saveNote(){

        viewModelScope.launch {

            try {

                if (currentNote != null) {

                    noteUseCases.addNote(
                        Note(
                            title = noteTitle.value.text,
                            content = noteContent.value.text,
                            dateCreated = currentNote!!.dateCreated,
                            dateEdited = System.currentTimeMillis(),
                            id = currentNoteId
                        )
                    )

                } else {
                    noteUseCases.addNote(
                        Note(
                            title = noteTitle.value.text,
                            content = noteContent.value.text,
                            dateCreated = System.currentTimeMillis(),
                            dateEdited = System.currentTimeMillis(),
                            id = currentNoteId
                        )
                    )

                }

                _eventFlow.emit(UiEvent.ShowSuccessSnackBar)
            } catch (e: InvalidNoteException){

                _eventFlow.emit(UiEvent.ShowErrorSnackBar)

            }
        }
    }

    fun checkContentAndTitleChanges(): Boolean = noteTitle.value.text == originalTitle && noteContent.value.text == originalContent


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