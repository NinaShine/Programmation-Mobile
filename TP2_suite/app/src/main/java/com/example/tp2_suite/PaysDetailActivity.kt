package com.example.tp2_suite


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tp2_suite.Pays

class PaysDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pays_detail)

        val pays = intent.getParcelableExtra<Pays>("pays") ?: return

        val paysNom: TextView = findViewById(R.id.detail_pays_nom)
        val paysCapitale: TextView = findViewById(R.id.detail_pays_capitale)
        val paysPopulation: TextView = findViewById(R.id.detail_pays_population)
        val paysDrapeau: ImageView = findViewById(R.id.detail_pays_drapeau)
        val paysRegion: TextView = findViewById(R.id.detail_pays_region)
        val paysSousRegion: TextView = findViewById(R.id.detail_pays_sous_region)
        val paysMonnaie: TextView = findViewById(R.id.detail_pays_monnaie)
        val paysLangue: TextView = findViewById(R.id.detail_pays_langues)
        val btnBack: Button = findViewById(R.id.btn_back)

        paysNom.text = pays.nom
        paysCapitale.text = "Capitale: ${pays.capitale}"
        paysPopulation.text = "Population: ${pays.population}"
        paysDrapeau.setImageResource(pays.drapeauResId)
        paysRegion.text = "Région: ${pays.region}"
        paysSousRegion.text = "Sous-région: ${pays.sousRegion}"
        paysMonnaie.text = "Monnaie: ${pays.monnaie}"
        paysLangue.text = "Langue: ${pays.langue}"

        btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed()
        }
    }
}
