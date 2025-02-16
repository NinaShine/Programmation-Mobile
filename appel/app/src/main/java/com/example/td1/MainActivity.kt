package com.example.td1

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

//        // Création du layout principal
//        val layout = LinearLayout(this).apply {
//            orientation = LinearLayout.VERTICAL
//            setPadding(32, 32, 32, 32)
//        }
//
//        // Fonction pour ajouter un champ avec un label
//        fun addLabeledField(labelText: String, hint: String, inputType: Int = InputType.TYPE_CLASS_TEXT): EditText {
//            val label = TextView(this).apply { text = labelText }
//            val editText = EditText(this).apply {
//                this.hint = hint
//                this.inputType = inputType
//            }
//            layout.addView(label)
//            layout.addView(editText)
//            return editText
//        }
//
//        // Ajout des champs avec labels
//        val editNom = addLabeledField("Entrez votre nom :", "Nom")
//        val editPrenom = addLabeledField("Entrez votre prénom :", "Prénom")
//        val editAge = addLabeledField("Entrez votre âge :", "Âge", InputType.TYPE_CLASS_NUMBER)
//        val editDomaine = addLabeledField("Entrez votre domaine de compétences :", "Domaine de compétences")
//        val editPhone = addLabeledField("Entrez votre numéro de téléphone :", "Numéro de téléphone", InputType.TYPE_CLASS_PHONE)
//
//        // Bouton de validation
//        val btnValider = Button(this).apply { text = "Valider" }
//
//        // Ajout du bouton au layout
//        layout.addView(btnValider)
//
//        // Définition de l'affichage
//         setContentView(layout)
        // Récupération des champs de saisie et du bouton
        val editNom = findViewById<EditText>(R.id.Nom)
        val editPrenom = findViewById<EditText>(R.id.Prenom)
        val editAge = findViewById<EditText>(R.id.Age)
        val editDomaine = findViewById<EditText>(R.id.Competences)
        val editPhone = findViewById<EditText>(R.id.Telephone)
        val btnValider = findViewById<Button>(R.id.valider)

        // Ajout du comportement au bouton de validation
        btnValider.setOnClickListener {
            // Récupérer les valeurs saisies
            val nom = editNom.text.toString()
            val prenom = editPrenom.text.toString()
            val age = editAge.text.toString()
            val domaine = editDomaine.text.toString()
            val phone = editPhone.text.toString()

            // Afficher une boîte de dialogue de confirmation
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous valider votre saisie ?")
                .setPositiveButton("Oui") { _, _ ->
                    // ✅ Changer la couleur de fond des champs après validation
                    val validatedColor = Color.parseColor("#D1C4E9") // Violet clair
                    editNom.setBackgroundColor(validatedColor)
                    editPrenom.setBackgroundColor(validatedColor)
                    editAge.setBackgroundColor(validatedColor)
                    editDomaine.setBackgroundColor(validatedColor)
                    editPhone.setBackgroundColor(validatedColor)

                    // ✅ Lancer la deuxième activité avec les données saisies
                    val intent = Intent(this, SecondActivity::class.java).apply {
                        putExtra("NOM", nom)
                        putExtra("PRENOM", prenom)
                        putExtra("AGE", age)
                        putExtra("DOMAINE", domaine)
                        putExtra("PHONE", phone)
                    }
                    startActivity(intent)
                }
                .setNegativeButton("Annuler") { dialog, _ ->
                    dialog.dismiss() // Fermer la boîte de dialogue sans rien faire
                }
                .create()

            // ✅ Afficher la boîte de dialogue
            alertDialog.show()
        }
    }
}
