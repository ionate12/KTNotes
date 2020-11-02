package com.example.ktnotes.model

import androidx.recyclerview.widget.DiffUtil
import com.example.ktnotes.db.Note
import com.example.ktnotes.util.toDateString
import java.util.*

data class NoteCell(val id: String, //id should be immutable
                    var starred: Boolean,
                    var title: String,
                    var content: String,
                    var dateTimeCreate: Date,
                    var dateTimeUpdate: Date
) {

    fun displayDate(): String = dateTimeUpdate.toDateString()

    //Applying Diff Callback for recycler View
    object DiffCallback: DiffUtil.ItemCallback<NoteCell>() {
        override fun areItemsTheSame(oldItem: NoteCell, newItem: NoteCell): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteCell, newItem: NoteCell): Boolean = oldItem == newItem
    }

    companion object {
        fun fromNote(note: Note) = NoteCell(note.id, note.starred, note.title, note.content, note.dateTimeCreate, note.dateTimeUpdate)
        fun toNote(noteCell: NoteCell) = noteCell.let { Note(it.id, it.starred, it.title, it.content, it.dateTimeCreate, it.dateTimeUpdate) }
        fun sortByFromString(value: String): NoteCell.SortBy {
            return NoteCell.SortBy.values().find { it.value == value } ?: NoteCell.SortBy.TitleAZ
        }
    }

    enum class SortBy(val value: String) {

        TitleAZ("Sort By Title A - Z"),
        TitleZA("Sort By Title Z - A"),
        LastUpdated("Sort By Last Updated")

    }

}