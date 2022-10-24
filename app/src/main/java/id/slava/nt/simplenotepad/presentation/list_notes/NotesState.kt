package id.slava.nt.simplenotepad.presentation.list_notes


import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import id.slava.nt.simplenotepad.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.DateCreated(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
