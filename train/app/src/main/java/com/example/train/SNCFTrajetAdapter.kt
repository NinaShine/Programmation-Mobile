package com.example.train

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.train.api.Journey

class SNCFTrajetAdapter(
    private var trajets: List<Journey>,
    private val onClick: (Journey) -> Unit // ✅ Ajout du paramètre pour le clic
) : RecyclerView.Adapter<SNCFTrajetAdapter.TrajetViewHolder>() {

    class TrajetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trajetInfoTextView: TextView = itemView.findViewById(R.id.trajetInfoTextView)
        val heureTextView: TextView = itemView.findViewById(R.id.heureTextView)
        val prixTextView: TextView = itemView.findViewById(R.id.prixTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrajetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trajet, parent, false)
        return TrajetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrajetViewHolder, position: Int) {
        val trajet = trajets[position]
        val depart = trajet.sections.firstOrNull()?.from?.name ?: "Inconnu"
        val arrivee = trajet.sections.lastOrNull()?.to?.name ?: "Inconnu"
        val heureDepart = "${trajet.departureDateTime.substring(9, 11)}h${trajet.departureDateTime.substring(11, 13)}"
        val heureArrivee = "${trajet.arrivalDateTime.substring(9, 11)}h${trajet.arrivalDateTime.substring(11, 13)}"

        // ✅ Utilisation du prix aléatoire si non fourni
        val prix = if (trajet.fare?.total?.value != null && trajet.fare.total.value != "0.0") {
            "${trajet.fare.total.value} €"
        } else {
            val randomPrice = (20..100).random()
            "$randomPrice €"
        }

        holder.trajetInfoTextView.text = "$depart ➝ $arrivee"
        holder.heureTextView.text = "Départ : $heureDepart | Arrivée : $heureArrivee"
        holder.prixTextView.text = "Prix : $prix"

        // ✅ Rendre chaque élément cliquable
        holder.itemView.setOnClickListener {
            onClick(trajet)
        }
    }

    override fun getItemCount(): Int = trajets.size
}
