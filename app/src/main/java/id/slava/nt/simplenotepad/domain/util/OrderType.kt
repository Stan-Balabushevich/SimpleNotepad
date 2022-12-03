package id.slava.nt.simplenotepad.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
