package com.syla.teajournal

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTeaScreen(
    teaViewModel: TeaViewModel,
    onNavigateBack: () -> Unit,
    teaId: Int? = null
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var brewingTemperature by remember { mutableStateOf("") }
    var steepTime by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(3f) }
    var notes by remember { mutableStateOf("") }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = teaId) {
        if (teaId != null && teaId != 0) {
            val tea = teaViewModel.getTeaById(teaId).first()
            tea?.let {
                name = it.name
                type = it.type
                brewingTemperature = it.brewingTemperature
                steepTime = it.steepTime
                rating = it.rating.toFloat() // Fixed conversion
                notes = it.notes
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (teaId == null) "Add New Tea" else "Edit Tea") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    if (teaId != null) {
                        IconButton(
                            onClick = { showDeleteConfirmationDialog = true },
                            enabled = teaId != 0
                        ) {
                            Icon(Icons.Filled.Delete, "Delete Tea")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Tea Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tea Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // Tea Type Field
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Tea Type") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // Brewing Temperature Field
            OutlinedTextField(
                value = brewingTemperature,
                onValueChange = { brewingTemperature = it },
                label = { Text("Brewing Temperature (°C)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            // Steep Time Field
            OutlinedTextField(
                value = steepTime,
                onValueChange = { steepTime = it },
                label = { Text("Steep Time (minutes)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            // Rating Slider
            Text(text = "Rating: ${rating.toInt()}/5")
            Slider(
                value = rating,
                onValueChange = { rating = it },
                valueRange = 1f..5f,
                steps = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // Notes Field
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = {
                    if (name.isBlank() || type.isBlank() ||
                        brewingTemperature.isBlank() || steepTime.isBlank()) {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val teaToSave = Tea(
                        id = teaId ?: 0,
                        name = name,
                        type = type,
                        brewingTemperature = brewingTemperature,
                        steepTime = steepTime,
                        rating = rating.toInt(), // Convert back to Int
                        notes = notes,
                        dateTasted = System.currentTimeMillis()
                    )

                    if (teaId == null) {
                        teaViewModel.addTea(teaToSave)
                        Toast.makeText(context, "Tea added!", Toast.LENGTH_SHORT).show()
                    } else {
                        teaViewModel.updateTea(teaToSave)
                        Toast.makeText(context, "Tea updated!", Toast.LENGTH_SHORT).show()
                    }
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (teaId == null) "Save Tea" else "Update Tea")
            }
        }

        // Delete Confirmation Dialog
        if (showDeleteConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmationDialog = false },
                title = { Text("Delete Tea?") },
                text = { Text("Are you sure you want to delete this tea entry?") },
                confirmButton = {
                    Button(
                        onClick = {
                            teaId?.let { id ->
                                teaViewModel.deleteTea(Tea(
                                    id = id,
                                    name = "",
                                    type = "",
                                    brewingTemperature = "",
                                    steepTime = "",
                                    rating = 0,
                                    notes = "",
                                    dateTasted = 0
                                ))
                                Toast.makeText(context, "Tea deleted!", Toast.LENGTH_SHORT).show()
                                onNavigateBack()
                            }
                            showDeleteConfirmationDialog = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteConfirmationDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}