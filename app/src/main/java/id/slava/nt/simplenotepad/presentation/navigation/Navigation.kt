package id.slava.nt.simplenotepad.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import id.slava.nt.simplenotepad.presentation.add_edit_note.AddEditNoteScreen
import id.slava.nt.simplenotepad.presentation.list_notes.NotesListScreen

@Composable
fun Navigation(sharedText: String?) {

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
                    // if needs more arguments
                    "?noteId={noteId}&getSharedText={getSharedText}",
//                    "?noteId={noteId}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "getSharedText"
                ) {
                    type = NavType.BoolType
                    defaultValue = true
                }
            ),
            // navDeepLink needs any action for triggering note detail
            // screen while comes from the other site or app for sharing text. DeepLink effect.
            // how to work if need different screens need to be to opened?
            deepLinks = listOf(
                navDeepLink {
//                    uriPattern = "/{getSharedText}"
                    action = Intent.ACTION_SEND
//                    mimeType = "text/*"
                }
            )
        ) {
            AddEditNoteScreen(
                navController = navController,
                sharedText = sharedText
                // if not using savedHandleSate then need to pass arguments to view model then get them like params
//                noteId = it.arguments?.getInt("noteId") ?: -1,
            )

        }

    }

}