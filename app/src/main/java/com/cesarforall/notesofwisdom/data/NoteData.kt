package com.cesarforall.notesofwisdom.data

val sourceTypes: List<SourceType> = listOf(
    SourceType("Book", "page"),
    SourceType("Movie", "minute"),
    SourceType("Video", "minute")
)

data class SourceType(
    val name: String,
    val location: String
)

data class Note(
    val text: String = "",
    val author: String = "",
    val sourceType: SourceType? = null,
    val source: String = "",
    val reference: String = ""
)
