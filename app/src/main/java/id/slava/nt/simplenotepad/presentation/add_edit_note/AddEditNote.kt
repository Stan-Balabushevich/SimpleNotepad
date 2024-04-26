package id.slava.nt.simplenotepad.presentation.add_edit_note

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.presentation.add_edit_note.components.TransparentHintTextField
import id.slava.nt.simplenotepad.presentation.util.NoteAlertDialog
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToolbarView(
    navController: NavController,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
    viewModel: AddEditNoteViewModel
) {

    val context = LocalContext.current
    val openBackArrowDialog = remember { mutableStateOf(false)  }
    val openDeleteDialog = remember { mutableStateOf(false)  }

    val keyboardController = LocalSoftwareKeyboardController.current


    if (openBackArrowDialog.value){

       NoteAlertDialog(
           title = stringResource(R.string.discard_changes),
           openedDialog = { dialogState -> openBackArrowDialog.value = dialogState },
           onConfirmButtonClicked = {navController.navigateUp()})

    }

    if (openDeleteDialog.value){

        NoteAlertDialog(
            title = stringResource(R.string.delete_note_question),
            openedDialog = { dialogState ->
                openDeleteDialog.value = dialogState },
            onConfirmButtonClicked = {
                viewModel.deleteNote()
                navController.navigateUp()})

    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {

            if(viewModel.checkContentAndTitleNotChanged()){
                navController.navigateUp()
            } else{
                openBackArrowDialog.value = true
            }

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
                viewModel.shareNote(context)

            }) {
                Icon(
                    imageVector =  Icons.Filled.Share,
                    contentDescription = stringResource(id = R.string.back_arrow),
                    tint = tint
                )
            }

            IconButton(onClick = {
                viewModel.checkTitleAndSaveNote(context.getString(R.string.default_title))
                keyboardController?.hide()

            }) {
                Icon(
                    imageVector =  Icons.Filled.Save,
                    contentDescription = stringResource(id = R.string.save_note),
                    tint = tint
                )
            }
            IconButton(onClick = {
                openDeleteDialog.value = true

            }) {
                Icon(
                    imageVector =  Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete_note),
                    tint = tint
                )
            }

        }

    }
}


@Composable
fun NoteContentView(
    viewModel: AddEditNoteViewModel,
    sharedText: String?){

    val context = LocalContext.current

// can get intent extras like this as well. from activity safer?
//    val activity = LocalContext.current as Activity
//    val data = intent.getStringExtra(Intent.EXTRA_TEXT)

    LaunchedEffect(key1 = true) {

        if(viewModel.sharedText.value){
            if (sharedText != null && sharedText.isNotBlank()){
                viewModel.titleChanged(sharedText.trim().substringBefore(" "))
                viewModel.contentChanged(sharedText)
            }
        }
        else{
            viewModel.setTitleValue(NoteTextFieldState(hint = context.getString(R.string.enter_title)))
            viewModel.setContentValue(NoteTextFieldState(hint = context.getString(R.string.enter_content)))
        }

    }

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){

        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = titleState.text,
            hint = titleState.hint,
            onValueChange = {
                            viewModel.titleChanged(it)
            },
            onFocusChange = {
                            viewModel.titleFocusChanged(it)

            },
            isHintVisible = titleState.isHintVisible,
            singleLine = true,
            textStyle = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = contentState.text,
            hint = contentState.hint,
            onValueChange = {
                            viewModel.contentChanged(it)
            },
            onFocusChange = {
                            viewModel.contentFocusChanged(it)
            },
            isHintVisible = contentState.isHintVisible,
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
                viewModel = koinViewModel<AddEditNoteViewModel>()
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
            NoteContentView(
                viewModel = koinViewModel(),
                sharedText = "deep link text"
            )
        }
    }
}