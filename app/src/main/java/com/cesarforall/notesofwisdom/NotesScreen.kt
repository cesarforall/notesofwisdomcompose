package com.cesarforall.notesofwisdom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cesarforall.notesofwisdom.ui.home.HomeScreen
import com.cesarforall.notesofwisdom.ui.noteForm.NoteFormScreen

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
fun NotesBottomAppBar(
    leftButtonText: String = "",
    rightButtonText: String = "",
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.padding(16.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (leftButtonText != "") {
                LeftButton(
                    onClick = onLeftButtonClick,
                    text = "",
                    modifier = Modifier.weight(1F)
                )
            } else {
                Spacer(Modifier.weight(1F))
            }
            Spacer(Modifier.size(8.dp))
            if (rightButtonText != "") {
                RightButton(
                    onClick = onRightButtonClick,
                    text = rightButtonText,
                    modifier = Modifier.weight(1F)
                )
            } else {
                Spacer(Modifier.weight(1F))
            }
        }
    }
}

@Composable
fun LeftButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.Black),
        shape = RectangleShape,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text, color = Color.Black)
    }
}

@Composable
fun RightButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.Black),
        shape = RectangleShape,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text, color = Color.Black)
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = { NotesTopAppBar() },
        bottomBar = {
            when {
                currentRoute?.startsWith(NotesScreen.Start.name) == true -> {
                    NotesBottomAppBar(
                        leftButtonText = "",
                        rightButtonText = "New Note",
                        onLeftButtonClick = {  },
                        onRightButtonClick = { navController.navigate(NotesScreen.Form.name) }
                    )
                }
                currentRoute?.startsWith(NotesScreen.Form.name) == true -> {
                    NotesBottomAppBar(
                        leftButtonText = "",
                        rightButtonText = "Back",
                        onLeftButtonClick = {  },
                        onRightButtonClick = { navController.navigate(NotesScreen.Start.name) }
                    )
                }
                else -> Text("Hello")
            }
        },
        modifier = Modifier.padding()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NotesScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NotesScreen.Start.name) {
                HomeScreen(navController = navController)
            }

            composable(route = NotesScreen.Form.name) {
                NoteFormScreen()
            }

            composable(
                route = "${NotesScreen.Form.name}?noteId={noteId}",
                arguments = listOf(navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
                NoteFormScreen(noteId = noteId)
            }
        }
    }
}
