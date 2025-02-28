package com.example.tp2_suite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailPaysFragment : Fragment() {

    private lateinit var paysNom: TextView
    private lateinit var paysCapitale: TextView
    private lateinit var paysPopulation: TextView
    private lateinit var paysDrapeau: ImageView
    private lateinit var paysRegion: TextView
    private lateinit var paysSousRegion: TextView
    private lateinit var paysMonnaie: TextView
    private lateinit var paysLangue: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_detail_pays, container, false)

        paysNom = view.findViewById(R.id.detail_pays_nom)
        paysCapitale = view.findViewById(R.id.detail_pays_capitale)
        paysPopulation = view.findViewById(R.id.detail_pays_population)
        paysDrapeau = view.findViewById(R.id.detail_pays_drapeau)
        paysRegion = view.findViewById(R.id.detail_pays_region)
        paysSousRegion = view.findViewById(R.id.detail_pays_sous_region)
        paysMonnaie = view.findViewById(R.id.detail_pays_monnaie)
        paysLangue = view.findViewById(R.id.detail_pays_langues)

        // Gérer le bouton Retour
        val btnRetour = view.findViewById<Button>(R.id.btn_retour)
        btnRetour.setOnClickListener {
            // Masquer le fragment de détails
            val detailContainer = requireActivity().findViewById<View>(R.id.fragment_detail)
            detailContainer.visibility = View.GONE
        }

        val pays = arguments?.getParcelable<Pays>("pays")

        if (pays != null) {
            Log.d("DetailPaysFragment", "✅ Pays reçu : ${pays.nom}")
            afficherDetails(pays)
        } else {
            Log.e("DetailPaysFragment", "❌ Aucun pays reçu !")
        }

        return view
    }



    fun afficherDetails(pays: Pays) {
        paysNom.text = pays.nom
        paysCapitale.text = "Capitale: ${pays.capitale}"
        paysPopulation.text = "Population: ${pays.population}"
        paysDrapeau.setImageResource(pays.drapeauResId)
        paysRegion.text = "Région: ${pays.region}"
        paysSousRegion.text = "Sous-région: ${pays.sousRegion}"
        paysMonnaie.text = "Monnaie: ${pays.monnaie}"
        paysLangue.text = "Langue: ${pays.langue}"
    }
}
