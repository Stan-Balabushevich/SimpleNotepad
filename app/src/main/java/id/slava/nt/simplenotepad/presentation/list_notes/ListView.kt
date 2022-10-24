package id.slava.nt.simplenotepad.presentation.list_notes

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.ui.theme.SimpleNotepadTheme

@Composable
fun NotesList(notes: List<Note>,
              onNoteItemSelected: (Note) -> Unit) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = notes) { note ->
            NoteCard(note = note, onNoteItemSelected = onNoteItemSelected)
        }
    }
}


@Composable
private fun NoteCard(note: Note, onNoteItemSelected: (Note) -> Unit) {

    Card(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        NoteCardContent(note, onNoteItemSelected)
    }

}

@Composable
private fun NoteCardContent(note: Note, onNoteItemSelected: (Note) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Row() {
                Icon(
                    imageVector =  Icons.Filled.EditNote,
                    contentDescription = "Edit note",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onNoteItemSelected(note) }
                )
                Column {
                    Text(text = stringResource(
                         R.string.created,
                        note.dateCreated.toString().repeat(6)
                    ) ,

                        style = TextStyle(fontSize = 12.sp)
                    )
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                        textAlign = TextAlign.End,
                        maxLines = 2,
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                    )
                }

            }

            if (expanded) {
                Column() {

                    Text(
                        text = stringResource(
                            R.string.edited,
                            note.dateEdited.toString().repeat(6)
                        ),
                        style = TextStyle(fontSize = 12.sp)
                    )

                    Text(
                        text = note.content ,
                        maxLines = 3,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier
                            .clickable { onNoteItemSelected(note) }
                    )
                }
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess
                else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    SimpleNotepadTheme {
        NotesList(notes = notesTest, onNoteItemSelected = {})
    }
}

val notesTest = List(10) { Note( id = it,
    title = "Title Composem ipsum color sit lazy, $it",
    content = ("Composem ipsum color sit lazy, " +
            "padding theme elit, sed do bouncy. ")
        .repeat(4), dateCreated = it.toLong(), dateEdited = it.toLong() ) }