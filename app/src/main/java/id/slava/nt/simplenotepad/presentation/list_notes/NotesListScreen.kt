package id.slava.nt.simplenotepad.presentation.list_notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.SearchBy
import id.slava.nt.simplenotepad.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = koinViewModel(),
    navController: NavController, ){


//    compose state
//    val state = viewModel.state.value
    // flow state
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.errors.collect { error ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = error.asString(context)
            )
        }
    }

    Scaffold(
        // to see snack bar!
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                ExpandableSearchView(
                    searchDisplay = "",
                    onSearchDisplayChanged = {
                        viewModel.setSearchText(it)},
                    onSearchDisplayClosed = {
                        viewModel.setSearchText("") },
                    onSearchBy = { searchBy ->
                        when(searchBy){
                            SearchBy.TITLE
                            -> viewModel.setSearchBy(searchBy)
                            SearchBy.CONTENT
                            -> viewModel.setSearchBy(searchBy)
                        }
                    },
                    onSortBy ={ order ->
                        when(order){
                            NoteOrder.Title
                            -> viewModel.orderBy(order)
                            NoteOrder.DateCreated
                            -> viewModel.orderBy(order)
                            NoteOrder.DateEdited ->
                                viewModel.orderBy(order)
                        }

                    } ,
                viewModel = viewModel)
            }
        },
        content = { NotesList(notes = state.notes

        , onNoteItemSelected = { note ->
                // arguments saved in savedHandleState
                navController.navigate(Screen.AddEditNoteScreen.route
                        + "?noteId=${note.id}&getSharedText=${false}")
        }) },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(28.dp),
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route
                                + "?getSharedText=${false}")
                }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.ftb_desc)
                )
            }
        }
    )
}
