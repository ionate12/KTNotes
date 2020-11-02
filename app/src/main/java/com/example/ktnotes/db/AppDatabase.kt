package com.example.ktnotes.db

import android.content.Context
import androidx.room.*
import java.util.*

@Database(entities = [Note::class], version = AppDatabase.DB_VERSION)
@TypeConverters(AppDatabase.Converters::class)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "KTNotesDB"
        private var INSTANCE: AppDatabase? = null
        fun initInstance(appContext: Context) {
            INSTANCE = Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME).build()
        }
        fun getInstance(): AppDatabase = INSTANCE ?: throw Throwable("ROOM-DB is not initialized yet.")
    }

    abstract fun noteDAO(): NoteDAO



    /**
     * Type converter to convert Date -> Long when storing to DB and vice versa...
     */
    class Converters {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}


