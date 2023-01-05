package id.slava.nt.simplenotepad.presentation.util


import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import id.slava.nt.simplenotepad.R

@Composable
fun NoteAlertDialog(
    modifier: Modifier = Modifier,
    title: String = "",
    message: String = "",
    confirmButton: String = stringResource(R.string.yes),
    dismissButton: String = stringResource(R.string.cancel),
    onConfirmButtonClicked: () -> Unit,
    openedDialog: (Boolean) -> Unit,
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            openedDialog(false)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text( text = message)
        },
        confirmButton = {
            Button(

                onClick = {
                    onConfirmButtonClicked()
                }) {
                Text( text = confirmButton)
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    openedDialog(false)
                }) {
                Text(text = dismissButton)
            }
        }
    )

}




