package com.example.tp3

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tp3.database.UserDatabase
import com.example.tp3.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InscriptionFragment(navController: NavController) {
    val context = LocalContext.current

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val interets = listOf("Sport", "Musique", "Lecture", "Jeux vidéo", "Voyage")
    val selected = remember { mutableStateMapOf<String, Boolean>() }
    interets.forEach { selected.putIfAbsent(it, false) }

    var alertMessage by remember { mutableStateOf<String?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }

    if (alertMessage != null) {
        AlertDialog(
            onDismissRequest = { alertMessage = null },
            title = { Text("Erreur") },
            text = { Text(alertMessage!!) },
            confirmButton = {
                TextButton(onClick = { alertMessage = null }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Créer votre Compte",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text(" Identifiant ", style = MaterialTheme.typography.labelLarge) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "commence par une lettre, max 10 caractères",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Column {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Mot de passe", style = MaterialTheme.typography.labelLarge) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(icon, contentDescription = "Toggle Password Visibility")
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "* Doit contenir au moins 6 caractères",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text("Nom", style = MaterialTheme.typography.labelLarge) },
                    leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = prenom,
                    onValueChange = { prenom = it },
                    label = { Text("Prénom", style = MaterialTheme.typography.labelLarge) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dateNaissance,
                    onValueChange = { dateNaissance = it },
                    label = { Text("Date de naissance", style = MaterialTheme.typography.labelLarge) },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = telephone,
                    onValueChange = { telephone = it },
                    label = { Text("Téléphone", style = MaterialTheme.typography.labelLarge) },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", style = MaterialTheme.typography.labelLarge) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Centres d'intérêt :", style = MaterialTheme.typography.titleMedium)

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    interets.forEach { interet ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = selected[interet] == true,
                                onCheckedChange = { isChecked -> selected[interet] = isChecked }
                            )
                            Text(interet)
                        }
                    }
                }

                Button(
                    onClick = {
                        // Vérification locale
                        if (!login.matches(Regex("^[A-Za-z][A-Za-z0-9_]{0,9}$"))) {
                            alertMessage = "Lidentifiant doit commencer par une lettre et contenir au maximum 10 caractères."
                            return@Button
                        }
                        if (password.length < 6) {
                            alertMessage = "Le mot de passe doit contenir au moins 6 caractères."
                            return@Button
                        }
                        if (!email.matches(Regex("^[A-Za-z0-9+_.%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))) {
                            alertMessage = "Adresse email invalide."
                            return@Button
                        }
                        if (!telephone.matches(Regex("^[0-9]{6,15}$"))) {
                            alertMessage = "Le téléphone doit contenir uniquement des chiffres (6 à 15)."
                            return@Button
                        }

                        val user = User(
                            login = login,
                            password = password,
                            nom = nom,
                            prenom = prenom,
                            dateNaissance = dateNaissance,
                            telephone = telephone,
                            email = email,
                            centresInteret = selected.filter { it.value }.keys.joinToString(", ")
                        )
                        val db = UserDatabase.getDatabase(context)
                        CoroutineScope(Dispatchers.IO).launch {
                            val existingEmail = db.userDao().getUserByEmail(email)
                            if (existingEmail != null) {
                                alertMessage = "Cette adresse email est déjà associée à un compte"
                                return@launch
                            }
                            val existingLogin = db.userDao().getUserByLogin(login)
                            if (existingLogin != null) {
                                alertMessage = "Ce login est déjà utilisé"
                                return@launch
                            }

                            val resultId = db.userDao().insertUser(user)
                            if (resultId > 0) {
                                android.util.Log.d("ROOM_DEBUG", "Utilisateur inséré : $user")
                                withContext(Dispatchers.Main) {
                                    navController.navigate("affichage")
                                }
                            } else {
                                alertMessage = "Erreur lors de l'enregistrement"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Soumettre", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)

                ) {
                    Text("Retour à l'accueil")
                }
            }

        }

    }
}

fun saveUser(context: Context, user: User) {
    val db = UserDatabase.getDatabase(context)
    CoroutineScope(Dispatchers.IO).launch {
        db.userDao().insertUser(user)
    }
}