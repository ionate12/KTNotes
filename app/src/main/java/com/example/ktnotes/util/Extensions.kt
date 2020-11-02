package com.example.ktnotes.util

import com.example.ktnotes.model.NoteCell

fun List<NoteCell>.sort(type: NoteCell.SortBy): List<NoteCell> {
    if (this.isNullOrEmpty()) return this //No need to sort.
    return when (type) {
        NoteCell.SortBy.TitleAZ -> { this.sortedBy { it.title }}
        NoteCell.SortBy.TitleZA -> { this.sortedByDescending { it.title }}
        NoteCell.SortBy.LastUpdated -> {this.sortedByDescending {  it.dateTimeUpdate }}
    }
}

