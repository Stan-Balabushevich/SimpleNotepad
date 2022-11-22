package id.slava.nt.simplenotepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import id.slava.nt.simplenotepad.presentation.navigation.Navigation
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNotepadTheme {

                Navigation()

            }
        }
    }
}


