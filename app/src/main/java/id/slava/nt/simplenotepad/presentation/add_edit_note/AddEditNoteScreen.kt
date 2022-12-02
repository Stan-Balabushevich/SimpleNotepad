package id.slava.nt.simplenotepad.presentation.add_edit_note

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import id.slava.nt.simplenotepad.R
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = koinViewModel(),
//    noteId: Int,
    navController: NavController) {

// if not using savedHandleSate then need to pass arguments to view model then get them like params
//    val viewModel = koinViewModel<AddEditNoteViewModel>( parameters = { parametersOf(noteId) })

    val scaffoldState: ScaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {

                is AddEditNoteViewModel.UiEvent.ShowSuccessSnackBar ->
                 scaffoldState.snackbarHostState.showSnackbar(message = context.getString(R.string.saved_success))
                is AddEditNoteViewModel.UiEvent.ShowErrorSnackBar ->
                    scaffoldState.snackbarHostState.showSnackbar(message = context.getString(R.string.save_error))
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                ToolbarView(
                    navController = navController,
                    viewModel = viewModel
                )

            }
        },
        content = {

            NoteContentView(viewModel =  viewModel)

        }
    )
}