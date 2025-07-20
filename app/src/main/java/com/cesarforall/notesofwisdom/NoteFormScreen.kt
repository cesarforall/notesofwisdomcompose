package com.cesarforall.notesofwisdom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.SourceType
import com.cesarforall.notesofwisdom.data.sourceTypes
import com.cesarforall.notesofwisdom.ui.theme.NotesOfWisdomTheme

@Composable
fun NoteFormScreen(
    note: Note?,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable() { mutableStateOf(note?.text?: "") }
    var author by rememberSaveable() { mutableStateOf(note?.author?: "") }
    var sourceTypeId by rememberSaveable { mutableIntStateOf(note?.sourceTypeId?: 0) }
    var source by rememberSaveable() { mutableStateOf(note?.source?: "") }
    var reference by rememberSaveable { mutableStateOf(note?.reference?: "") }

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        val scrollState = rememberScrollState()
        var selectedType by remember { mutableStateOf<SourceType?>(null) }
        val isEnabled = sourceTypeId != 0
        val lightestGray = Color(0xFFF0F0F0)

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp))
        {
            TextField(
                value = text,
                onValueChange = { text = it },
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
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                modifier = modifier.fillMaxWidth()
            )
            SourceTypeDropdown(
                sourceTypes = sourceTypes,
                selected = selectedType,
                onSelected = {
                    selectedType = it
                    sourceTypeId = it.id
                }
            )
            TextField(
                value = source,
                onValueChange = { source = it },
                label = { Text("Source") },
                enabled = isEnabled,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = if (isEnabled) Color.White else Color.LightGray,
                    focusedContainerColor = if (isEnabled) Color.White else Color.LightGray,
                    disabledContainerColor = lightestGray,
                    disabledTextColor = Color.DarkGray,
                    disabledLabelColor = Color.Gray
                ),
                modifier = modifier.fillMaxWidth()
            )
            TextField(
                value = reference,
                onValueChange = { reference = it },
                enabled = isEnabled,
                label = { Text(
                    when (selectedType?.location) {
                        "page" -> "Page"
                        "minute" -> "Minute"
                        else -> "Unknown"
                    }
                ) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = lightestGray,
                    disabledTextColor = Color.DarkGray,
                    disabledLabelColor = Color.Gray
                ),
                modifier = modifier.fillMaxWidth()
            )

            Spacer(Modifier.size(24.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
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

@Preview(showBackground = true)
@Composable
fun PreviewNoteFormScreen() {
    val note = Note(1, "Nota de prueba", "César", null, "", "")

    NotesOfWisdomTheme {
        NoteFormScreen(note = note, modifier = Modifier)
    }
}