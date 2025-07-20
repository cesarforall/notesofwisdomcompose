package com.cesarforall.notesofwisdom.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

val sourceTypes: List<SourceType> = listOf(
    SourceType(0, "Unknown", "unknown"),
    SourceType(1, "Book", "page"),
    SourceType(2, "Movie", "minute"),
    SourceType(3, "Video", "minute")
)

@Entity
data class SourceType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String
)

@Entity
data class Note(
    @PrimaryKey val id: Int,
    val text: String = "",
    val author: String = "",
    val sourceTypeId: Int?,
    val source: String = "",
    val reference: String = ""
)

data class NoteWithSourceType(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "sourceTypeId",
        entityColumn = "id"
    )
    val sourceType: SourceType?
)