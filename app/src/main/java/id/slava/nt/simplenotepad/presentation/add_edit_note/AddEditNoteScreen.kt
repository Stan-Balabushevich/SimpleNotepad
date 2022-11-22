package id.slava.nt.simplenotepad.presentation.add_edit_note

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = koinViewModel(),
//    noteId: Int,
    navController: NavController) {

// if not using savedHandleSate then need to pass arguments to view model then get them like params
//    val viewModel = koinViewModel<AddEditNoteViewModel>( parameters = { parametersOf(noteId) })

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                ToolbarView(
                    navController = navController,
                    shareNote = {},
                    saveNote = {},
                    deleteNote = {},
                    viewModel = viewModel
                )

            }
        },
        content = {

            NoteContentView(viewModel =  viewModel)

        }
    )
}