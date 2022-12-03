package id.slava.nt.simplenotepad.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.slava.nt.simplenotepad.presentation.add_edit_note.AddEditNoteScreen
import id.slava.nt.simplenotepad.presentation.list_notes.NotesListScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route
    ) {
        composable(route = Screen.NotesScreen.route) {
            NotesListScreen(
                navController = navController)
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
                // if not using savedHandleSate then need to pass arguments to view model then get them like params
//                noteId = it.arguments?.getInt("noteId") ?: -1,
            )

        }

    }

}