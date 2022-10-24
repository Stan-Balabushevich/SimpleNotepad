package id.slava.nt.simplenotepad.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import id.slava.nt.simplenotepad.domain.models.Note

class AddEditNoteViewModel: ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteId = mutableStateOf(-1)
//    val noteId: State<Int> = _noteId

    fun setId(id: Int){
        _noteId.value = id
    }

    init {

        val notesTest = List(10) { Note( id = it,
            title = "Title Composem  $it",
            content = ("Composem ipsum color sit lazy, " +
                    "padding theme elit, sed do bouncy. ")
                .repeat(4), dateCreated = it.toLong(), dateEdited = it.toLong() ) }


        val noteId = _noteId.value

//        if(noteId != -1) {
//            viewModelScope.launch {
//                noteUseCases.getNote(noteId)?.also { note ->
//                    _noteTitle.value = noteTitle.value.copy(
//                        text = note.title,
//                        isHintVisible = false
//                    )
//                    _noteContent.value = _noteContent.value.copy(
//                        text = note.content,
//                        isHintVisible = false
//                    )
//                }
//            }
//        }

        if(noteId != -1 ){

            notesTest[noteId].also { note ->
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