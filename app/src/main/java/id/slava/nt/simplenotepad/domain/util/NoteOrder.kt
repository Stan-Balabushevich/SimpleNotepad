package id.slava.nt.simplenotepad.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): NoteOrder(orderType)
    class DateCreated(orderType: OrderType): NoteOrder(orderType)
    class DateEdited(orderType: OrderType): NoteOrder(orderType)


    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Title -> Title(orderType)
            is DateCreated -> DateCreated(orderType)
            is DateEdited -> DateEdited(orderType)
        }
    }
}
