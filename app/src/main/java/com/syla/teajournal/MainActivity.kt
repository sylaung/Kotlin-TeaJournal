package com.syla.teajournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable // Crucial import for clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syla.teajournal.data.TeaRepository
import com.syla.teajournal.ui.theme.TeaJournalTheme
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { TeaRepository(database.teaDao()) }
    private val teaViewModel: TeaViewModel by viewModels {
        TeaViewModel.provideFactory(repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeaJournalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "teaList") {
                        composable("teaList") {
                            TeaListScreen(
                                teaViewModel = teaViewModel,
                                onAddTeaClick = { navController.navigate("addEditTea") },
                                onTeaClick = { teaId -> navController.navigate("addEditTea/$teaId") }
                            )
                        }
                        composable("addEditTea") {
                            AddEditTeaScreen(
                                teaViewModel = teaViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("addEditTea/{teaId}") { backStackEntry ->
                            val teaId = backStackEntry.arguments?.getString("teaId")?.toIntOrNull()
                            AddEditTeaScreen(
                                teaViewModel = teaViewModel,
                                onNavigateBack = { navController.popBackStack() },
                                teaId = teaId
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeaListScreen(
    teaViewModel: TeaViewModel,
    onAddTeaClick: () -> Unit,
    onTeaClick: (Int) -> Unit
) {
    val teaList by teaViewModel.allTeas.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tea Journal") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTeaClick) {
                Icon(Icons.Filled.Add, "Add new tea")
            }
        }
    ) { paddingValues ->
        if (teaList.isEmpty()) {
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                Text(
                    text = "No teas logged yet. Click + to add one!",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(teaList) { tea ->
                    TeaListItem(tea = tea) { clickedTea ->
                        onTeaClick(clickedTea.id)
                    }
                }
            }
        }
    }
}

@Composable
fun TeaListItem(tea: Tea, onClick: (Tea) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxSize()
            .clickable { onClick(tea) }
    ) {
        Text(text = "Name: ${tea.name}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Type: ${tea.type}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Rating: ${tea.rating}/5", style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TeaJournalTheme {
        TeaListScreen(
            teaViewModel = TeaViewModel(TeaRepository(AppDatabase.getDatabase(LocalContext.current).teaDao())),
            onAddTeaClick = {},
            onTeaClick = {}
        )
    }
}