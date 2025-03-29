package com.example.tp3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tp3.database.UserDatabase
import com.example.tp3.model.Planning
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlanningSyntheseScreen(navController: NavController, login: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val monthFormat = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()) }
    val dayOfWeekFormat = remember { SimpleDateFormat("EEE", Locale.getDefault()) }

    var selectedDate by remember { mutableStateOf(Date()) }
    val calendar = Calendar.getInstance().apply { time = selectedDate }

    var planningForDay by remember { mutableStateOf<Planning?>(null) }
    val scrollState = rememberScrollState()

    // Obtenir planning pour la date sélectionnée
    LaunchedEffect(selectedDate) {
        val db = UserDatabase.getDatabase(context)
        planningForDay = db.planningDao().getPlanningByLoginAndDate(login, dateFormat.format(selectedDate))
    }

    // Navigation entre mois
    var currentMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    fun getDatesForMonth(month: Int, year: Int): List<Date> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        return (1..maxDay).map {
            cal.set(Calendar.DAY_OF_MONTH, it)
            cal.time
        }.filter { !it.before(Date()) }
    }

    val dates = getDatesForMonth(currentMonth, currentYear)

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // En-tête mois avec flèches
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    if (currentMonth == 0) {
                        currentMonth = 11
                        currentYear -= 1
                    } else {
                        currentMonth -= 1
                    }
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Mois précédent")
                }

                Text(
                    text = monthFormat.format(Calendar.getInstance().apply {
                        set(Calendar.MONTH, currentMonth)
                        set(Calendar.YEAR, currentYear)
                    }.time),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = {
                    if (currentMonth == 11) {
                        currentMonth = 0
                        currentYear += 1
                    } else {
                        currentMonth += 1
                    }
                }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Mois suivant")
                }
            }

            // Affichage horizontal des jours
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                dates.forEach { date ->
                    val isSelected = dateFormat.format(date) == dateFormat.format(selectedDate)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { selectedDate = date }
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else Color.Transparent,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp)
                    ) {
                        Text(dayOfWeekFormat.format(date), color = Color.Gray)
                        Text(
                            text = SimpleDateFormat("d").format(date),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // Planning du jour sélectionné
            Text(
                "Planning du ${dateFormat.format(selectedDate)}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            val slots = listOf(
                "08h - 10h" to (planningForDay?.creneau1 ?: ""),
                "10h - 12h" to (planningForDay?.creneau2 ?: ""),
                "14h - 16h" to (planningForDay?.creneau3 ?: ""),
                "16h - 18h" to (planningForDay?.creneau4 ?: "")
            )

            Column(modifier = Modifier.padding(top = 16.dp)) {
                slots.forEach { (time, content) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = time, fontWeight = FontWeight.Bold)
                            Text(text = if (content.isBlank()) "Aucune activité" else content)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val selectedDateStr = dateFormat.format(selectedDate)
                    navController.navigate("planning/$login/$selectedDateStr")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modifier Planning")
            }



            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Retour à l'accueil")
            }
        }
    }
}
