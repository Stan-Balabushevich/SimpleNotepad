package id.slava.nt.simplenotepad.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.slava.nt.simplenotepad.presentation.add_edit_note.AddEditNoteScreen
import id.slava.nt.simplenotepad.presentation.add_edit_note.AddEditNoteViewModel
import id.slava.nt.simplenotepad.presentation.list_notes.NotesListScreen
import id.slava.nt.simplenotepad.presentation.list_notes.NotesListViewModel

@Composable
fun Navigation(
    noteListViewModel: NotesListViewModel,
    context: Context,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route
    ) {
        composable(route = Screen.NotesScreen.route) {
            NotesListScreen(noteListViewModel, context, navController)
        }
        composable(
            route = Screen.AddEditNoteScreen.route +
                    "?noteId={noteId}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                })
        ) {
            AddEditNoteScreen(
                navController = navController,
                context = context,
                noteId = it.arguments?.getInt("noteId") ?: -1
            )

        }

    }

}