package com.example.tp3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tp3.database.UserDatabase
import com.example.tp3.model.Planning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun PlanningScreen(navController: NavController, login: String, date: String) {
    val context = LocalContext.current
    var creneau1 by remember { mutableStateOf("") }
    var creneau2 by remember { mutableStateOf("") }
    var creneau3 by remember { mutableStateOf("") }
    var creneau4 by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val db = UserDatabase.getDatabase(context)
        val planning = db.planningDao().getPlanningByLoginAndDate(login, date)
        planning?.let {
            creneau1 = it.creneau1
            creneau2 = it.creneau2
            creneau3 = it.creneau3
            creneau4 = it.creneau4
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Planning pour le $date", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = creneau1,
            onValueChange = { creneau1 = it },
            label = { Text("08h-10h") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = creneau2,
            onValueChange = { creneau2 = it },
            label = { Text("10h-12h") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = creneau3,
            onValueChange = { creneau3 = it },
            label = { Text("14h-16h") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = creneau4,
            onValueChange = { creneau4 = it },
            label = { Text("16h-18h") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val db = UserDatabase.getDatabase(context)
                    val existing = db.planningDao().getPlanningByLoginAndDate(login, date)

                    if (existing != null) {
                        db.planningDao().updatePlanning(
                            Planning(
                                id = existing.id,
                                login = login,
                                date = date,
                                creneau1 = creneau1,
                                creneau2 = creneau2,
                                creneau3 = creneau3,
                                creneau4 = creneau4
                            )
                        )
                    } else {
                        db.planningDao().insertPlanning(
                            Planning(
                                login = login,
                                date = date,
                                creneau1 = creneau1,
                                creneau2 = creneau2,
                                creneau3 = creneau3,
                                creneau4 = creneau4
                            )
                        )
                    }

                    navController.navigate("planning_synthese/$login")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider")
        }
    }
}

