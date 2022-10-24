package id.slava.nt.simplenotepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.presentation.add_edit_note.AddEditNoteViewModel
import id.slava.nt.simplenotepad.presentation.list_notes.NotesListViewModel
import id.slava.nt.simplenotepad.presentation.navigation.Navigation
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNotepadTheme {

                val noteListViewModel = viewModel<NotesListViewModel>()
//                val addEditNoteViewModel = viewModel<AddEditNoteViewModel>()
                val context = LocalContext.current
                var notesList: List<Note> = emptyList()

//                lifecycleScope.launchWhenStarted {
//                    noteListViewModel.searchBy.collect{
////                        Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
//                        notesList = noteListViewModel.notesList.value
//                    }
//                }

                Navigation(
                    noteListViewModel = noteListViewModel,
                    context = context,
//                    addEditNoteViewModel = addEditNoteViewModel
                )

            }
        }
    }
}


