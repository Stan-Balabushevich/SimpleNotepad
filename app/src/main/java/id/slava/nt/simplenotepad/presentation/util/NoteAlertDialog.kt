package id.slava.nt.simplenotepad.presentation.util


import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoteAlertDialog(
    modifier: Modifier = Modifier,
    title: String = "Default Title",
    message: String = "",
    confirmButton: String = "Yes",
    dismissButton: String = "Cancel",
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




