package com.example.ktnotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ktnotes.BaseNoteViewModel
import com.example.ktnotes.db.AppDatabase
import com.example.ktnotes.db.Note
import com.example.ktnotes.model.NoteCell
import com.example.ktnotes.util.sort
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class NoteListViewModel: BaseNoteViewModel() {
    val dataSource: MutableLiveData<List<NoteCell>> = MutableLiveData()
    private var sortType: NoteCell.SortBy = NoteCell.SortBy.TitleAZ
    init {
        loadDataSource()
    }

    private fun loadDataSource() {
        cd.add(
            noteDAO.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    list.map(NoteCell::fromNote).sort(sortType).let { dataSource.value = it }
                }, /*OnNext*/
                    Throwable::printStackTrace, //OnError
                    { /*OnComplete DoNothing*/ })
        )
    }

    fun sort(sortType: NoteCell.SortBy) {
        this.sortType = sortType
        dataSource.value = dataSource.value?.sort(sortType) ?: listOf()
    }

    /**
     * Create a new note to DB and  return this note via callback function
     */
    fun createNote(onCompleted: (NoteCell) -> Unit){
        val note = Note(UUID.randomUUID().toString(), false, "New Title", "", Date(), Date())
        cd.add(noteDAO.insert(note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onCompleted(NoteCell.fromNote(note)) })
    }

    override fun onCleared() {
        super.onCleared()
        cd.dispose() //Dispose all existing observables when clearing the viewModel
    }

}
