package id.slava.nt.simplenotepad

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import id.slava.nt.simplenotepad.presentation.ExpandableSearchView
import id.slava.nt.simplenotepad.presentation.NotesList
import id.slava.nt.simplenotepad.presentation.notesTest
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme

class MainActivity : ComponentActivity() {

//    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNotepadTheme {

                val viewModel = viewModel<MainViewModel>()
                val context = LocalContext.current

//                val text = viewModel.searchText.collectAsState()

                lifecycleScope.launchWhenStarted {
                    viewModel.searchBy.collect{
                        Toast.makeText(context,it,Toast.LENGTH_SHORT).show()

                    }
                }

                ListScreen(viewModel,context)

            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ListScreen(viewModel: MainViewModel, context: Context){

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
                            -> Toast.makeText(context, it,Toast.LENGTH_SHORT).show()
                            context.getString(R.string.sort_edited)
                            -> Toast.makeText(context, it,Toast.LENGTH_SHORT).show()
                            context.getString(R.string.sort_edited) ->
                                Toast.makeText(context, it,Toast.LENGTH_SHORT).show()
                        }

                    } )

            }
        },
        content = { NotesList(notes = notesTest, onNoteItemSelected = {
            // send it to edit note screen through nav component as argument
            Toast.makeText(context,it.title,Toast.LENGTH_SHORT).show()
        }) },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(28.dp),
                onClick = { /* ... */ }) {
                Icon(Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.ftb_desc))
            }
        }
    )
}

