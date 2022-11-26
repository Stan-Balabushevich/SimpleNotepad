package id.slava.nt.simplenotepad.domain.usecase

import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DeleteNoteTest {

//    val fakeNoteRepository = mock<NoteRepository>()
    private lateinit var fakeNoteRepository: NoteRepository
    private lateinit var testNote: Note
    private lateinit var deleteNote: DeleteNote
    private lateinit var getNote: GetNote
    private lateinit var addNote: AddNote

    @Before
    fun setUp() {

        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        addNote = AddNote(fakeNoteRepository)
        getNote = GetNote(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    id = index,
                    title = c.toString(),
                    content = c.toString(),
                    dateCreated = index.toLong(),
                    dateEdited = index.toLong()
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeNoteRepository.insertNote(it) }
            testNote = getNote(3)!!
        }



    }

    @Test
    fun `Check if note deleted`() = runBlocking {

        val testNoteId = 3

        deleteNote.invoke(testNote)
        val actualNote = getNote(testNoteId)
        val expected: Note? = null

        assertThat(actualNote).isEqualTo(expected)

    }







}