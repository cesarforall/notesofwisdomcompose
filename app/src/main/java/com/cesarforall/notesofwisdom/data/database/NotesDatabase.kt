package com.cesarforall.notesofwisdom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.SourceType
import com.cesarforall.notesofwisdom.data.dao.NoteDao

@Database(entities = [SourceType::class, Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        @Volatile
        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}