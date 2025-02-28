package com.example.tp2_suite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaysAdapter(private val paysList: List<Pays>, private val onClick: (Pays) -> Unit) :
    RecyclerView.Adapter<PaysAdapter.PaysViewHolder>() {

    class PaysViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val paysNom: TextView = view.findViewById(R.id.pays_nom)
        val paysDrapeau: ImageView = view.findViewById(R.id.pays_drapeau)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pays, parent, false)
        return PaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaysViewHolder, position: Int) {
        val pays = paysList[position]
        holder.paysNom.text = pays.nom
        holder.paysDrapeau.setImageResource(pays.drapeauResId)

        holder.itemView.setOnClickListener {
            onClick(pays)
        }
    }

    override fun getItemCount() = paysList.size
}
