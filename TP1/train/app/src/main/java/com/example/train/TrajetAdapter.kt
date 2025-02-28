package com.example.train

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrajetAdapter(private var trajets: List<Trajet>, private val onClick: (Trajet) -> Unit) :
    RecyclerView.Adapter<TrajetAdapter.TrajetViewHolder>() {

    class TrajetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trajetInfoTextView: TextView = itemView.findViewById(R.id.trajetInfoTextView)
        val heureTextView: TextView = itemView.findViewById(R.id.heureTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrajetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trajet, parent, false)
        return TrajetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrajetViewHolder, position: Int) {
        val trajet = trajets[position]
        holder.trajetInfoTextView.text = "${trajet.villeDepart} ➝ ${trajet.villeArrivee} - ${trajet.date}"
        holder.heureTextView.text = trajet.heure?.let { "Heure : $it" } ?: "Heure : Non spécifiée"

        // Gérer le clic sur un trajet
        holder.itemView.setOnClickListener {
            onClick(trajet)
        }
    }

    override fun getItemCount(): Int = trajets.size
}
