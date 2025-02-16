package com.example.train

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.train.api.Journey

class SummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val trajet = intent.getParcelableExtra<Journey>("trajet")
        val selectedSeat = intent.getIntExtra("selectedSeat", -1)
        val prix = intent.getIntExtra("prix", 0) // ✅ Récupération du prix aléatoire

        val summaryTextView = findViewById<TextView>(R.id.summaryTextView)
        val confirmButton = findViewById<Button>(R.id.confirmFinalButton)
        val retourButton = findViewById<Button>(R.id.retour)
        val prixTextView = findViewById<TextView>(R.id.prixTextView)

        // ✅ Affichage du récapitulatif de la réservation
        val summaryText = """
            🚆 Trajet : ${trajet?.sections?.firstOrNull()?.from?.name} ➝ ${trajet?.sections?.lastOrNull()?.to?.name}
            📅 Date : ${trajet?.departureDateTime?.substring(0, 8)}
            ⏰ Heure : ${trajet?.departureDateTime?.substring(9, 11)}h${trajet?.departureDateTime?.substring(11, 13)}
            💺 Siège sélectionné : ${if (selectedSeat != -1) selectedSeat else "Aucun"}
        """.trimIndent()

        summaryTextView.text = summaryText
        prixTextView.text = "${prix} €"

        // ✅ Aller à la page de paiement
        confirmButton.setOnClickListener {
            println("✅ Bouton 'Confirmer la réservation' cliqué !") // Vérifier si ça s'affiche
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("trajet", trajet)
                putExtra("selectedSeat", selectedSeat)
                putExtra("prix", prix)
            }
            startActivity(intent)
        }


        // ✅ Bouton Retour
        retourButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Ferme `SummaryActivity` pour éviter un empilement d'activités
        }
    }
}
