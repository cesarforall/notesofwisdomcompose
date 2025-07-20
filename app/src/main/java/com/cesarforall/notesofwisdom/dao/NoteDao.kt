package com.cesarforall.notesofwisdom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cesarforall.notesofwisdom.data.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id = (:id)")
    fun getById(id: Int): Note

    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)
}