package com.example.td1

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Récupérer les données envoyées par `MainActivity`
        val nom = intent.getStringExtra("NOM")
        val prenom = intent.getStringExtra("PRENOM")
        val age = intent.getStringExtra("AGE")
        val domaine = intent.getStringExtra("DOMAINE")
        val phone = intent.getStringExtra("PHONE")

        // Afficher les données dans des TextView
        findViewById<TextView>(R.id.textNom).text = "Nom : $nom"
        findViewById<TextView>(R.id.textPrenom).text = "Prénom : $prenom"
        findViewById<TextView>(R.id.textAge).text = "Âge : $age"
        findViewById<TextView>(R.id.textDomaine).text = "Domaine : $domaine"
        findViewById<TextView>(R.id.textPhone).text = "Téléphone : $phone"

        findViewById<Button>(R.id.btnOk).setOnClickListener {
            val phoneNumber = intent.getStringExtra("PHONE")
            val intent = Intent(this, ThirdActivity::class.java).apply {
                putExtra("PHONE", phoneNumber)
            }
            startActivity(intent)
        }


        // Bouton Retour → Revient à `MainActivity`
        findViewById<Button>(R.id.btnRetour).setOnClickListener {
            finish() // Ferme l'activité et retourne à `MainActivity`
        }
    }
}