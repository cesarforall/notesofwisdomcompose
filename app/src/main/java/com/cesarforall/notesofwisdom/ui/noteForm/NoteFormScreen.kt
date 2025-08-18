package com.cesarforall.notesofwisdom.ui.noteForm

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.SourceType
import com.cesarforall.notesofwisdom.data.sourceTypes
import com.cesarforall.notesofwisdom.ui.AppViewModelProvider
import com.cesarforall.notesofwisdom.ui.theme.NotesOfWisdomTheme
import kotlinx.coroutines.launch

@Composable
fun NoteFormScreen(
    modifier: Modifier = Modifier,
    noteId: Int? = null,
    noteFormViewModel: NoteFormViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    val noteUiState = noteFormViewModel.noteUiState

    LaunchedEffect(noteId) {
        if (noteId != null) {
            noteFormViewModel.loadNote(noteId)
        } else {
            noteFormViewModel.clearForm()
        }
    }

    var selectedType by remember { mutableStateOf<SourceType?>(null) }
    LaunchedEffect(noteUiState.sourceTypeId) {
        selectedType = sourceTypes.find { it.id == noteUiState.sourceTypeId }
    }

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        val scrollState = rememberScrollState()
        val isEnabled = noteUiState.sourceTypeId != null && noteUiState.sourceTypeId != 0
        val lightestGray = Color(0xFFF0F0F0)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp))
        {
            TextField(
                value = noteUiState.text,
                onValueChange = { noteFormViewModel.updateNote(noteUiState.copy(text = it)) },
                label = { Text("Text or phrase") },
                minLines = 5,
                maxLines = 5,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = noteUiState.author,
                onValueChange = { noteFormViewModel.updateNote(noteUiState.copy(author = it)) },
                label = { Text("Author") },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = modifier.fillMaxWidth()
            )
            SourceTypeDropdown(
                sourceTypes = sourceTypes,
                selected = selectedType,
                onSelected = {
                    selectedType = it
                    noteFormViewModel.updateNote(noteUiState.copy(sourceTypeId = it.id))
                }
            )
            TextField(
                value = noteUiState.source,
                onValueChange = { noteFormViewModel.updateNote(noteUiState.copy(source = it)) },
                label = { Text("Source") },
                enabled = isEnabled,
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = if (isEnabled) Color.White else Color.LightGray,
                    focusedContainerColor = if (isEnabled) Color.White else Color.LightGray,
                    disabledContainerColor = lightestGray,
                    disabledTextColor = Color.DarkGray,
                    disabledLabelColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = modifier.fillMaxWidth()
            )
            TextField(
                value = noteUiState.reference,
                onValueChange = { noteFormViewModel.updateNote(noteUiState.copy(reference = it)) },
                enabled = isEnabled,
                label = { Text(
                    when (selectedType?.location) {
                        "page" -> "Page"
                        "minute" -> "Minute"
                        else -> "Unknown"
                    }
                ) },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = lightestGray,
                    disabledTextColor = Color.DarkGray,
                    disabledLabelColor = Color.Gray
                ),
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(Modifier.size(56.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (noteId != null) {
                            noteFormViewModel.modifyNote()
                        } else {
                            noteFormViewModel.saveNote()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Save", color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceTypeDropdown(
    sourceTypes: List<SourceType>,
    selected: SourceType?,
    onSelected: (SourceType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selected?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Source type") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            sourceTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(
                        text = type.name,
                    ) },
                    onClick = {
                        onSelected(type)
                        expanded = false
                    },
                    modifier = Modifier.background(Color.White)
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewNoteFormScreen() {
    val note = Note(1, "Nota de prueba", "César", null, "", "")

    NotesOfWisdomTheme {
        NoteFormScreen()
    }
}