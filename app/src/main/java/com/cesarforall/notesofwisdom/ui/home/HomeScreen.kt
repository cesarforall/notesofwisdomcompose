package com.cesarforall.notesofwisdom.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cesarforall.notesofwisdom.NotesScreen
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.sourceTypes
import com.cesarforall.notesofwisdom.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val notes = homeUiState.notesList

    NotesList(
        notes = notes,
        onDeleteClick = { note -> homeViewModel.deleteNote(note) },
        onEditClick = { note ->
            navController.navigate("${NotesScreen.Form.name}?noteId=${note.id}")
        },
        modifier = modifier
    )
}

@Composable
fun NotesList(
    notes: List<Note>,
    onDeleteClick: (Note) -> Unit,
    onEditClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) { note ->
            NoteCard(
                note,
                onEditClick = { onEditClick(note) },
                onShareClick = {  },
                onDeleteClick = { onDeleteClick(note) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note: Note,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false }
        ) {
            Card(
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Delete note")
                    Spacer(Modifier.height(16.dp))
                    Text("¿Are you sure you want to delete this note?")

                    Spacer(Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = {
                            onDeleteClick()
                            showDialog = false
                        }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = note.text,
                    fontSize = TextUnit.Unspecified,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildCitation(note),
                        fontStyle = FontStyle.Italic,
                        fontSize = TextUnit.Unspecified,
                        color = Color.DarkGray
                    )
                }
            }
            Column {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit note",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = {
                        val noteWithCitation = if (buildCitation(note).isNotBlank()) {
                            note.text + "\n" + buildCitation(note)
                        } else {
                            note.text
                        }
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, noteWithCitation)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Add note",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete note",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

fun buildCitation(note: Note) : String {
    return buildString {
        if (note.author.isNotBlank()) {
            append("— ")
            append(note.author)
        }

        if (note.source.isNotBlank()) {
            append(", \"${note.source}\"")

            val type = sourceTypes.find { it.id == note.sourceTypeId } ?: sourceTypes[0]

            if (type != sourceTypes[0]) {
                append(" (${type.name})")

                val reference = note.reference

                if (reference.isNotBlank()) {
                    when (type.location) {
                        "page" -> append(", p. $reference")
                        "minute" -> append(", min. $reference")
                        else -> append(", $reference")
                    }
                }
            } else {
                append(" (${type.name})")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotesList() {
    val sampleNotes = listOf(
        Note(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc lacinia rutrum felis. Mauris consectetur, sapien vel suscipit finibus, mauris ipsum malesuada massa, quis aliquam massa urna a magna. Fusce turpis odio, auctor id scelerisque id, feugiat ac leo. Pellentesque pretium nulla id vestibulum ornare. Quisque sollicitudin consequat convallis. Morbi consectetur id odio lacinia suscipit. Donec vulputate leo eu commodo volutpat. Nam nec imperdiet erat.", "Aragorn", 1,"ESDLA", "1"),
        Note(2, "Todos los caminos llegan a Roma", "César", 0, "ESDLA", "10")
    )
    NotesList(
        sampleNotes,
        onDeleteClick = {},
        onEditClick = {}
    )
}