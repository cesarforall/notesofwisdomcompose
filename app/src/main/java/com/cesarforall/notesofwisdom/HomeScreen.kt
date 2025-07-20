package com.cesarforall.notesofwisdom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.sourceTypes

@Composable
fun HomeScreen(
    notes: List<Note>,
    modifier: Modifier = Modifier
) {
    NotesList(
        notes = notes,
        modifier = modifier
    )
}

@Composable
fun NotesList(notes: List<Note>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) { note ->
            NoteCard(
                note,
                onEditClick = { /* ... */ }
            )
        }
    }
}

@Composable
fun NoteCard(note: Note, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.text,
                fontSize = TextUnit.Unspecified,
                fontFamily = FontFamily.Serif,
                color = Color.Black
            )

            val citation = buildString {
                append("— ")
                append(note.author)
                append(", ")
                append("\"${note.source}\"")

                val type = sourceTypes.find { it.id == note.sourceTypeId } ?: sourceTypes[0]
                append(" (${type.name})")

                val reference = note.reference

                when (type.location) {
                    "page" -> append(", p. $reference")
                    "minute" -> append(", min. $reference")
                    else -> append(", $reference")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = citation,
                    fontStyle = FontStyle.Italic,
                    fontSize = TextUnit.Unspecified,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val sampleNotes = listOf(
        Note(1, "No todos los que vagan están perdidos", "Aragorn", 1,"ESDLA", "1"),
        Note(2, "Todos los caminos llegan a Roma", "César", 0, "ESDLA", "10")
    )
    HomeScreen(notes = sampleNotes)
}