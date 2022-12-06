package id.slava.nt.simplenotepad.domain.usecase

import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.repository.NoteRepository
import id.slava.nt.simplenotepad.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.DateCreated
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder) {
                is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                is NoteOrder.DateCreated -> notes.sortedByDescending { it.dateCreated }
                is NoteOrder.DateEdited -> notes.sortedByDescending { it.dateEdited }
            }

        }
    }


//    operator fun invoke(
//        noteOrder: NoteOrder = NoteOrder.DateCreated(OrderType.Descending)
//    ): Flow<List<Note>> {
//        return repository.getNotes().map { notes ->
//            when(noteOrder.orderType) {
//                is OrderType.Ascending -> {
//                    when(noteOrder) {
//                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
//                        is NoteOrder.DateCreated -> notes.sortedBy { it.dateCreated}
//                        is NoteOrder.DateEdited -> notes.sortedBy { it.dateEdited}
//                    }
//                }
//                is OrderType.Descending -> {
//                    when(noteOrder) {
//                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
//                        is NoteOrder.DateCreated -> notes.sortedBy { it.dateCreated}
//                        is NoteOrder.DateEdited -> notes.sortedBy { it.dateEdited}
//                    }
//                }
//            }
//        }
//    }
}