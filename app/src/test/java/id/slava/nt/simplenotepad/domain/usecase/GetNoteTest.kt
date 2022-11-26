package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import com.google.common.truth.Truth.assertThat

class GetNoteTest {

    private lateinit var fakeNoteRepository: NoteRepository
    private lateinit var testNote: Note
    private lateinit var getNote: GetNote

    @Before
    fun setUp() {

        fakeNoteRepository = mock()
        getNote = GetNote(fakeNoteRepository)
        testNote = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5)

    }

    @Test
    fun `should return same note as in repository`() = runBlocking {

        val testNoteId = 5

        Mockito.`when`(fakeNoteRepository.getNoteById(testNote.id!!)).thenReturn(testNote)

        val expected = getNote.invoke(testNoteId)
        val actual = Note(title = "Test title", content = "test content", dateCreated = 12L, dateEdited = 21L, id = 5)

        assertThat(actual).isEqualTo(expected)

    }
}