package com.example.tp3_suite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tp3_suite.ui.theme.Tp3_suiteTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Tp3_suiteTheme {
                val context = LocalContext.current

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("TP3_Suite") },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { padding ->
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)) {

                        FormScreen(
                            onSubmit = { userData ->
                                val filename = "user_data.json"
                                val json = Json.encodeToString(userData)
                                context.openFileOutput(filename, MODE_PRIVATE).use {
                                    it.write(json.toByteArray())
                                }

                                val intent = Intent(context, HtmlDownloadService::class.java)
                                intent.putExtra("data", json)
                                context.startService(intent)

                                val displayIntent = Intent(context, DisplayActivity::class.java)
                                displayIntent.putExtra("nomFichier", filename)
                                context.startActivity(displayIntent)
                            },
                            onCancel = {
                                
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FormScreen(
    onSubmit: (UserLinks) -> Unit,
    onCancel: () -> Unit
) {
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var perso by remember { mutableStateOf("") }
    var linkedIn by remember { mutableStateOf("") }
    var entreprise by remember { mutableStateOf("") }
    var entrepriseLinkedIn by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Formulaire utilisateur",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // ✅ Carte avec icône + introduction
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Icône utilisateur",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Vos informations personnelles",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Veuillez compléter tous les champs requis ci-dessous.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // ✅ Champs de saisie
        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Nom", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = prenom,
            onValueChange = { prenom = it },
            label = { Text("Prénom", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = perso,
            onValueChange = { perso = it },
            label = { Text("Page Web personnelle", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = linkedIn,
            onValueChange = { linkedIn = it },
            label = { Text("LinkedIn personnel", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = entreprise,
            onValueChange = { entreprise = it },
            label = { Text("Site de l’entreprise", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = entrepriseLinkedIn,
            onValueChange = { entrepriseLinkedIn = it },
            label = { Text("LinkedIn de l’entreprise", style = MaterialTheme.typography.labelLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // ✅ Boutons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    val data = UserLinks(nom, prenom, perso, linkedIn, entreprise, entrepriseLinkedIn)
                    onSubmit(data)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Valider")
            }
            OutlinedButton(
                onClick = onCancel,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Annuler")
            }
        }
    }
}
