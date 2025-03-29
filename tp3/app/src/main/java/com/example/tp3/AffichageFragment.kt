package com.example.tp3

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tp3.database.UserDatabase
import com.example.tp3.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AffichageFragment(navController: NavController) {
    val context = LocalContext.current
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = UserDatabase.getDatabase(context)
            user = db.userDao().getLastUser()

        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Données enregistrées",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (user != null) {
                        Text("Login : ${user!!.login}")
                        Text("Nom : ${user!!.nom}")
                        Text("Prénom : ${user!!.prenom}")
                        Text("Date de naissance : ${user!!.dateNaissance}")
                        Text("Téléphone : ${user!!.telephone}")
                        Text("Email : ${user!!.email}")
                        Text("Centres d'intérêt : ${user!!.centresInteret}")
                    } else {
                        Text("Aucun utilisateur trouvé")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Retour à l'inscription", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
