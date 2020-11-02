package com.example.ktnotes

import androidx.lifecycle.ViewModel
import com.example.ktnotes.db.AppDatabase
import com.example.ktnotes.db.Note
import com.example.ktnotes.db.NoteDAO
import com.example.ktnotes.model.NoteCell
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


//A Base ViewModel that's content some shared elements and methods for Note Module
open class BaseNoteViewModel: ViewModel(){
    internal val cd = CompositeDisposable()
    internal val noteDAO = AppDatabase.getInstance().noteDAO()

    fun saveNoteToDB(noteCell: NoteCell) {
        noteCell.also {
            it.dateTimeUpdate = Date()
            val note = NoteCell.toNote(it)
            cd.add(noteDAO.update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }
    override fun onCleared() {
        super.onCleared()
        cd.dispose()
    }
}