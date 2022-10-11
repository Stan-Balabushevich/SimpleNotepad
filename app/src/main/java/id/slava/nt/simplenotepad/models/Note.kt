package id.slava.nt.simplenotepad.models


data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
)