package com.example.agenda

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var currentDateText: TextView
    private lateinit var noEventsText: TextView
    private lateinit var addEventButton: Button

    private var selectedDate: String = ""
    private val eventsMap = HashMap<String, MutableList<String>>() // Stocke les événements
    private lateinit var adapter: EventsAdapter
    private var eventList = mutableListOf<String>()
    private val calendarEvents = ArrayList<EventDay>() // Liste des événements dans le calendrier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // 📌 Récupération des éléments du layout
        calendarView = findViewById(R.id.calendar)
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView)
        currentDateText = findViewById(R.id.currentDateText)
        noEventsText = findViewById(R.id.noEventsText)
        addEventButton = findViewById(R.id.addEventButton)

        // 📌 Configuration du RecyclerView pour afficher la liste des événements
        adapter = EventsAdapter(eventList) { position -> showDeleteConfirmationDialog(position) }
        eventsRecyclerView.layoutManager = LinearLayoutManager(this)
        eventsRecyclerView.adapter = adapter

        // 📅 Date du jour par défaut
        val today = Calendar.getInstance()
        selectedDate = getDateKey(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
        currentDateText.text = "Bonjour ! Nous sommes le ${formatDate(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH), today.get(Calendar.YEAR))}"

        // 📌 Gestion du clic sur une date dans le calendrier
        calendarView.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
            override fun onClick(calendarDay: CalendarDay) {
                val selectedCalendar = calendarDay.calendar
                val year = selectedCalendar.get(Calendar.YEAR)
                val month = selectedCalendar.get(Calendar.MONTH)
                val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

                selectedDate = getDateKey(year, month, dayOfMonth)
                loadEventsForDate(selectedDate)
            }
        })


        // ➕ Ajout d’un événement
        addEventButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivityForResult(intent, REQUEST_CODE_ADD_EVENT)
        }
    }

    // 📌 Charger les événements pour une date sélectionnée
    private fun loadEventsForDate(date: String) {
        eventList = eventsMap[date] ?: mutableListOf()
        adapter.updateEvents(eventList)

        // Affichage conditionnel du message "Aucun événement"
        noEventsText.visibility = if (eventList.isEmpty()) View.VISIBLE else View.GONE
    }

    // 📌 Ajout d’un événement et mise à jour du calendrier
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_EVENT && resultCode == RESULT_OK) {
            val eventDetails = data?.getStringExtra("eventDetails")
            if (eventDetails != null) {
                val eventsForDate = eventsMap[selectedDate] ?: mutableListOf()
                eventsForDate.add(eventDetails)
                eventsMap[selectedDate] = eventsForDate

                adapter.updateEvents(eventsForDate)
                markDateOnCalendar(selectedDate) // 🔴 Marquer la date en rouge
            }
        }
    }

    // 📌 Suppression avec boîte de dialogue de confirmation
    private fun showDeleteConfirmationDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Êtes-vous sûr de vouloir supprimer cet événement ?")

        builder.setPositiveButton("Oui") { _, _ ->
            eventList.removeAt(position)
            adapter.updateEvents(eventList)
            eventsMap[selectedDate] = eventList

            Toast.makeText(this, "Événement supprimé", Toast.LENGTH_SHORT).show()

            // ✅ Vérifier s'il reste des événements ce jour-là
            if (eventList.isEmpty()) {
                removeDateFromCalendar(selectedDate) // 🔴 Supprimer la couleur rouge si plus d'événements
            }
        }

        builder.setNegativeButton("Non") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // 📌 Enlever une date du calendrier si tous les événements sont supprimés
    private fun removeDateFromCalendar(date: String) {
        val dateParts = date.split("-")
        val calendar = Calendar.getInstance().apply {
            set(dateParts[0].toInt(), dateParts[1].toInt() - 1, dateParts[2].toInt())
        }

        val iterator = calendarEvents.iterator()
        while (iterator.hasNext()) {
            val eventDay = iterator.next()
            if (eventDay.calendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                eventDay.calendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                eventDay.calendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
            ) {
                iterator.remove()
                break
            }
        }

        calendarView.setEvents(calendarEvents)
    }

    // Formater la date pour l’affichage
    private fun formatDate(day: Int, month: Int, year: Int): String {
        return String.format("%02d-%02d-%04d", day, month + 1, year)
    }

    //  Générer une clé unique pour stocker les événements
    private fun getDateKey(year: Int, month: Int, day: Int): String {
        return "$year-${String.format("%02d", month + 1)}-${String.format("%02d", day)}"
    }

    companion object {
        const val REQUEST_CODE_ADD_EVENT = 1
    }

    // Marquer une date en rouge dans le calendrier lorsqu'un événement est ajouté
    private fun markDateOnCalendar(date: String) {
        val dateParts = date.split("-")
        val day = dateParts[2].toInt()
        val month = dateParts[1].toInt() - 1 // Mois commence à 0
        val year = dateParts[0].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val eventDay = EventDay(calendar, ContextCompat.getDrawable(this, R.drawable.ic_event_marker)!!)

        // Ajouter la date aux événements du calendrier
        calendarEvents.add(eventDay)
        calendarView.setEvents(calendarEvents)
    }
}
