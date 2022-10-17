package id.slava.nt.simplenotepad.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.presentation.components.TransparentHintTextField
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme

@Composable
fun ToolbarView(
    navController: NavController,
    shareNote: (Boolean) -> Unit,
    saveNote: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(
                imageVector =  Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back_arrow),
                tint = tint
            )
        }
        Row(
//            horizontalArrangement = Arrangement.End
            verticalAlignment = Alignment.CenterVertically

        ){

            IconButton(onClick = {
                shareNote(true)

            }) {
                Icon(
                    imageVector =  Icons.Filled.Share,
                    contentDescription = stringResource(id = R.string.back_arrow),
                    tint = tint
                )
            }

            IconButton(onClick = {
                saveNote(true)

            }) {
                Icon(
                    imageVector =  Icons.Filled.Save,
                    contentDescription = stringResource(id = R.string.back_arrow),
                    tint = tint
                )
            }

        }

    }
}


@Composable
fun NoteContentView(){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){

        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = "",
            hint = "titleState.hint",
            onValueChange = {

            },
            onFocusChange = {

            },
            isHintVisible = true,
            singleLine = true,
            textStyle = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = "",
            hint = "contentState.hint",
            onValueChange = {

            },
            onFocusChange = {

            },
            isHintVisible = true,
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxHeight()
        )




    }


}

@Preview
@Composable
fun ToolbarViewPreview() {
    SimpleNotepadTheme {
        Surface(
            color = MaterialTheme.colors.primary
        ) {
            ToolbarView(
                navController = NavController(LocalContext.current),
                shareNote = {},
                saveNote = {}
            )
        }
    }
}

@Preview
@Composable
fun NoteContentViewPreview() {
    SimpleNotepadTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            NoteContentView()
        }
    }
}