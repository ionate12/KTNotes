package com.example.ktnotes.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ktnotes.BaseNoteViewModel
import com.example.ktnotes.db.AppDatabase
import com.example.ktnotes.model.NoteCell
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class NoteDetailViewModel : BaseNoteViewModel() {
    private val TAG = this::class.simpleName
    val data: MutableLiveData<NoteCell> = MutableLiveData()
    private var didInit = false
    fun loadNote(noteId: String) {
        if (didInit) return
        didInit = true
        cd.add(noteDAO.findById(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ note ->
                NoteCell.fromNote(note).also { data.value = it }
            }, Throwable::printStackTrace))
    }


    fun saveNote() {
        Log.d(TAG, "saveNote")
        data.value?.also { saveNoteToDB(it) }
    }
}