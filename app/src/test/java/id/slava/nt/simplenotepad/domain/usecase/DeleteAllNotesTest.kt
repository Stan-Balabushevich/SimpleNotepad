package id.slava.nt.simplenotepad.domain.usecase

import com.google.common.truth.Truth.assertThat
import id.slava.nt.simplenotepad.data.repository.FakeNoteRepository
import id.slava.nt.simplenotepad.domain.models.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


import org.junit.Before
import org.junit.Test

class DeleteAllNotesTest {

    private lateinit var fakeRepository: FakeNoteRepository
    private lateinit var deleteAllNotes: DeleteAllNotes
    private lateinit var getNotes: GetNotes

    @Before
    fun setUp() {

        fakeRepository = FakeNoteRepository()
        deleteAllNotes = DeleteAllNotes(fakeRepository)
        getNotes = GetNotes(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    dateEdited = index.toLong(),
                    dateCreated = index.toLong()
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepository.insertNote(it) }
        }

    }

    @Test
    fun `Notes deleted`() = runBlocking {

        deleteAllNotes.invoke()

        assertThat(getNotes.invoke().first()).isEqualTo(emptyList<Note>())
    }


}