package com.example.train

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.train.api.Journey

class SeatSelectionActivity : ComponentActivity() {
    private val seatStates = mutableMapOf<Int, String>() // Stocke l'état des sièges
    private var selectedSeat: Int? = null // Siège sélectionné

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        val trajet = intent.getParcelableExtra<Journey>("trajet")
        val titleTextView = findViewById<TextView>(R.id.trainTitle)
        val seatGrid = findViewById<GridLayout>(R.id.seatGrid)
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val retourButton = findViewById<Button>(R.id.retour) // ✅ Correction : Trouver le bon bouton
        val selectedSeatTextView = findViewById<TextView>(R.id.selectedSeatTextView)

        titleTextView.text = "Choisissez un siège pour ${trajet?.sections?.firstOrNull()?.from?.name} ➝ ${trajet?.sections?.lastOrNull()?.to?.name}"

        val totalSeats = 30 // Nombre total de sièges
        val reservedSeats = setOf(3, 7, 12, 18, 22) // Exemples de sièges réservés

        seatGrid.columnCount = 3

        for (i in 1..totalSeats) {
            seatStates[i] = if (reservedSeats.contains(i)) "reserved" else "available"

            val seatImage = ImageView(this)
            seatImage.setImageResource(R.drawable.armchair)
            seatImage.setColorFilter(getSeatColor(seatStates[i]!!))

            seatImage.setOnClickListener {
                if (seatStates[i] == "reserved") return@setOnClickListener

                selectedSeat?.let { previousSeat ->
                    seatStates[previousSeat] = "available"
                    (seatGrid.getChildAt(previousSeat - 1) as ImageView).setColorFilter(getSeatColor("available"))
                }

                seatStates[i] = "selected"
                seatImage.setColorFilter(getSeatColor("selected"))
                selectedSeat = i

                selectedSeatTextView.text = "Siège sélectionné : $i"
                selectedSeatTextView.visibility = View.VISIBLE
            }

            val params = GridLayout.LayoutParams()
            params.width = 180
            params.height = 180
            params.setMargins(10, 10, 10, 10)
            seatImage.layoutParams = params

            seatGrid.addView(seatImage)
        }

        // ✅ Bouton de confirmation : Naviguer vers `SummaryActivity`
        confirmButton.setOnClickListener {
            if (selectedSeat == null) {
                titleTextView.text = "Veuillez sélectionner un siège !"
                titleTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            } else {
                val randomPrice = (20..100).random() // ✅ Génère un prix aléatoire
                val intent = Intent(this, SummaryActivity::class.java).apply {
                    putExtra("trajet", trajet)
                    putExtra("selectedSeat", selectedSeat)
                    putExtra("prix", randomPrice) // ✅ Envoyer le prix généré
                }
                startActivity(intent)
            }
        }

        // ✅ Gestion du bouton "Retour" : Retour à la liste des trajets
        retourButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // ✅ Redirige vers la page des trajets
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Ferme l'activité actuelle pour éviter d'empiler les pages
        }
    }

    private fun getSeatColor(state: String): Int {
        return when (state) {
            "available" -> ContextCompat.getColor(this, android.R.color.darker_gray)
            "reserved" -> ContextCompat.getColor(this, android.R.color.holo_red_dark)
            "selected" -> ContextCompat.getColor(this, android.R.color.holo_blue_light)
            else -> ContextCompat.getColor(this, android.R.color.darker_gray)
        }
    }
}
