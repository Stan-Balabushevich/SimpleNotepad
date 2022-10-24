package id.slava.nt.simplenotepad.presentation.list_notes

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.presentation.navigation.Screen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel,
    context: Context,
    navController: NavController
){

    val state = viewModel.state.value
    val listNotes = viewModel.stateTest.value


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                ExpandableSearchView(
                    searchDisplay = "",
                    onSearchDisplayChanged = {
                        viewModel.setSearchText(it)},
                    onSearchDisplayClosed = { },
                    onSearchBy = {
                        when(it){
                            context.getString(R.string.search_title)
                            -> viewModel.setSearchBy(TITLE)
                            context.getString(R.string.search_content)
                            -> viewModel.setSearchBy(CONTENT)
                        }
                    },
                    onSortBy ={
                        when(it){
                            context.getString(R.string.sort_created)
                            -> Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            context.getString(R.string.sort_edited)
                            -> Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            context.getString(R.string.sort_edited) ->
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }

                    } )

            }
        },
        content = { NotesList(notes = listNotes

        , onNoteItemSelected = { note ->
                navController.navigate(Screen.AddEditNoteScreen.route
                        + "?noteId=${note.id}")
//            // send it to edit note screen through nav component as argument
//            Toast.makeText(context,it.title, Toast.LENGTH_SHORT).show()
        }) },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(28.dp),
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.ftb_desc)
                )
            }
        }
    )
}