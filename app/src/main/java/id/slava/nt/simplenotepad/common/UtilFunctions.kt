package id.slava.nt.simplenotepad.common

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import id.slava.nt.simplenotepad.BuildConfig
import id.slava.nt.simplenotepad.R
import id.slava.nt.simplenotepad.domain.models.Note
import id.slava.nt.simplenotepad.domain.util.UiText
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

fun millisToDate(seconds: Long): String
        = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ROOT).format(seconds)


private fun writeTextTofile(context: Context, text: String){

    val path = context.filesDir
    File(path,"notes_list_snotepad.txt").writeText(text)

}

private fun shareNotesBuilder(noteList: List<Note>): String{

    val builder = StringBuilder()

    noteList.forEach {  note ->
        builder
            .appendLine(note.title)
            .appendLine(note.content)
            .appendLine()
    }
    return builder.toString()
}

fun shareFileCommon(context: Context, noteList: List<Note>): UiText{

    try{
        writeTextTofile(context,shareNotesBuilder(noteList))
    }catch (e: Exception){
        return UiText.StringResource(R.string.save_file_error)
    }

    val path = context.filesDir
    val file = File(path, "notes_list_snotepad.txt")

    if (file.exists()) {
        val uri = FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, "List of Notes from SimpleNotepad")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    } else{
        return UiText.StringResource(R.string.save_file_error)
    }
    return  UiText.StringResource(R.string.saved_success)
}