package com.example.train

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.train.api.Journey

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val trajetsList = intent.getParcelableArrayListExtra<Journey>("trajets") ?: arrayListOf()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewResults)
        val titleTextView = findViewById<TextView>(R.id.resultsTitle)

        titleTextView.text = "Trajets disponibles"

        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Ajout du clic pour ouvrir `SeatSelectionActivity`
        val adapter = SNCFTrajetAdapter(trajetsList) { trajet ->
            val intent = Intent(this, SeatSelectionActivity::class.java).apply {
                putExtra("trajet", trajet) // ✅ Passer les données du trajet
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        if (trajetsList.isEmpty()) {
            Toast.makeText(this, "Aucun train disponible", Toast.LENGTH_SHORT).show()
        }
    }
}
