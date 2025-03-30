package com.example.tp3_suite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.tp3_suite.ui.theme.Tp3_suiteTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class DisplayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nomFichier = intent.getStringExtra("nomFichier") ?: return
        val json = openFileInput(nomFichier).bufferedReader().use { it.readText() }
        val user = Json.decodeFromString<UserLinks>(json)

        setContent {
            Tp3_suiteTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "Résumé des informations",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("👤 Nom : ${user.nom}")
                                Text("👤 Prénom : ${user.prenom}")
                                Text("🌐 Page perso : ${user.perso}")
                                Text("🔗 LinkedIn : ${user.linkedIn}")
                                Text("🏢 Entreprise : ${user.entreprise}")
                                Text("🔗 LinkedIn Entreprise : ${user.entrepriseLinkedIn}")
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val html = try {
                                    File(filesDir, "perso.html").readText()
                                } catch (e: Exception) {
                                    "Erreur chargement HTML"
                                }

                                Text(
                                    text = "Contenu HTML de la page personnelle :",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = html,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
