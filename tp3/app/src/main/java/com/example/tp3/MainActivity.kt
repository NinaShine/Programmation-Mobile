package com.example.tp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp3.ui.theme.Tp3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Tp3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            StartScreen(navController)
        }
        composable("inscription") {
            InscriptionFragment(navController)
        }
        composable("affichage") {
            AffichageFragment(navController)
        }
        composable("connexion") {
            ConnexionScreen(navController)
        }
        composable("planning/{login}/{date}") { backStackEntry ->
            val login = backStackEntry.arguments?.getString("login") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            PlanningScreen(navController, login, date)
        }
        composable("planning_synthese/{login}") { backStackEntry ->
            val login = backStackEntry.arguments?.getString("login") ?: ""
            PlanningSyntheseScreen(navController, login)
        }
    }
}



@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("inscription") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inscription")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TP3") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController)
        }
    }
}

@Composable
fun StartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Bienvenue",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("inscription") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nouvelle inscription")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { navController.navigate("connexion") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Connexion")
        }
    }
}
