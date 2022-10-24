package id.slava.nt.simplenotepad.presentation.add_edit_note

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import id.slava.nt.simplenotepad.domain.models.Note

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
//    viewModel: AddEditNoteViewModel,
    noteId: Int,
    context: Context,
    navController: NavController
) {

    val viewModel = viewModel<AddEditNoteViewModel>()

    viewModel.setId(noteId)

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                ToolbarView(
                    navController = navController,
                    shareNote = {},
                    saveNote = {},
                    deleteNote = {}
                )

            }
        },
        content = {

            NoteContentView(titleState,contentState)
//            Toast.makeText(context,titleState.text,Toast.LENGTH_SHORT).show()
        }
    )
}