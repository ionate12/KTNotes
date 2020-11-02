package com.example.ktnotes.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Table, Model
 */
@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val starred: Boolean,
    val title: String,
    val content: String,
    val dateTimeCreate: Date,
    val dateTimeUpdate: Date
) {
}

/**
 * RoomDB Dao Interface, where to set some common queries
 */
@Dao
abstract class NoteDAO {

    /**
     * Get All Notes from DB,
     * Return type: Flowable will automatically emit data to subscribers whenever the Note Table has events.
     */
    @Query("SELECT * from Notes")
    abstract fun findAll(): Flowable<List<Note>>

    /**
     * Return type: Single will emit the data just once at the time it is subscribed.
     */
    @Query("SELECT * from Notes where id = :id")
    abstract fun findById(id: String): Single<Note>

    @Insert
    abstract fun insert(note: Note): Completable

    @Update
    abstract fun update(note: Note): Completable

    @Delete
    abstract fun delete(note: Note): Completable

    @Query("DELETE FROM Notes")
    abstract fun deleteAll()


}


/**
 * This class is used for fake data which is auto generated once at the first time
 */
object NoteSampleData {
    var sampleData = listOf(
        Note(
            UUID.randomUUID().toString(),
            false,
            "Sample 1",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            Date(),
            Date()
        ),
        Note(
            UUID.randomUUID().toString(),
            false,
            "Sample 2",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            Date(),
            Date()
        ),
        Note(
            UUID.randomUUID().toString(),
            true,
            "Sample 3",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            Date(),
            Date()
        ),
        Note(
            UUID.randomUUID().toString(),
            true,
            "Sample 4",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            Date(),
            Date()
        ),
        Note(
            UUID.randomUUID().toString(),
            true,
            "Sample 5",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            Date(),
            Date()
        ),
        Note(
            UUID.randomUUID().toString(),
            true,
            "Sample 6",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            Date(),
            Date()
        ),
    )


    fun initSampleData() {
        val dao = AppDatabase.getInstance().noteDAO()
        //Append a List of Completables (Observable) to a single flow and consume it.
        Completable.concat(sampleData.map { dao.insert(it) })
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe() // This will auto dispose
    }
}