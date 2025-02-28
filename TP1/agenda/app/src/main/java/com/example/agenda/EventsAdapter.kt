package com.example.agenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventsAdapter(
    private var events: List<String>,
    private val onItemClick: (Int) -> Unit // Ajout de l'écouteur pour la suppression
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
        holder.itemView.setOnClickListener {
            onItemClick(position) // Gérer le clic et demander confirmation de suppression
        }
    }

    override fun getItemCount(): Int = events.size

    fun updateEvents(newEvents: List<String>) {
        events = newEvents
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventText: TextView = itemView.findViewById(R.id.eventText)

        fun bind(event: String) {
            eventText.text = event
        }
    }
}
