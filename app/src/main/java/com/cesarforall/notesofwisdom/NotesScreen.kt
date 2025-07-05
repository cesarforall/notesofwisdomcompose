package com.cesarforall.notesofwisdom

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesarforall.notesofwisdom.data.Note
import com.cesarforall.notesofwisdom.data.sourceTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopAppBar() {
    Box {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    "Notes of Wisdom",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp),
            thickness = 2.dp,
            color = Color.Black
        )
    }
}

@Composable
fun NotesBottomAppBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "",
        )
    }
}

@Composable
fun NewNoteButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ink_pen_48dp),
            contentDescription = "Añadir nota",
            tint = Color.Black
        )
    }
}

enum class NotesScreen {
    Start,
    Form
}

@Composable
fun NotesApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = { NotesTopAppBar() },
        bottomBar = { NotesBottomAppBar() },
        floatingActionButton = {
            NewNoteButton(onClick = {
                navController.navigate(NotesScreen.Form.name)
            })
        },
        modifier = Modifier.padding()
    ) { innerPadding ->

        val sampleNotes = listOf(
            Note("No todos los que vagan están perdidos", "Aragorn", sourceTypes.get(1),"ESDLA", "1"),
            Note("Todos los caminos llegan a Roma", "César", sourceTypes.get(0), "ESDLA", "10")
        )

        NavHost(
            navController = navController,
            startDestination = NotesScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NotesScreen.Start.name) {
                HomeScreen(notes = sampleNotes)
            }

            composable(route = NotesScreen.Form.name) {
                val note = Note()
                NoteFormScreen(
                    note = note
                )
            }
        }
    }
}
