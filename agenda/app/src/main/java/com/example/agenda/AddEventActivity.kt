package com.example.agenda

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import java.util.*

class AddEventActivity : ComponentActivity() {

    private lateinit var eventTitleInput: EditText
    private lateinit var eventLocationInput: EditText
    private lateinit var eventTimeInput: EditText
    private lateinit var emojiSelector: Spinner
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        eventTitleInput = findViewById(R.id.eventTitleInput)
        eventLocationInput = findViewById(R.id.eventLocationInput)
        eventTimeInput = findViewById(R.id.eventTimeInput)
        emojiSelector = findViewById(R.id.emojiSelector)
        confirmButton = findViewById(R.id.confirmButton)

        val selectedDate = intent.getStringExtra("selectedDate")

        // Options d'emoji
        val emojis = listOf("🎉", "🎂", "📅", "🏆", "🎶", "🏖️", "\uD83C\uDDE9\uD83C\uDDFF ","\uD83C\uDDF1\uD83C\uDDE7")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, emojis)
        emojiSelector.adapter = adapter

        // Sélectionner l'heure avec un `TimePicker`
        eventTimeInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    eventTimeInput.setText(String.format("%02d:%02d", hourOfDay, minute))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }

        // Ajouter l'événement
        confirmButton.setOnClickListener {
            val title = eventTitleInput.text.toString()
            val location = eventLocationInput.text.toString()
            val time = eventTimeInput.text.toString()
            val emoji = emojiSelector.selectedItem.toString()

            if (title.isNotEmpty() && location.isNotEmpty() && time.isNotEmpty()) {
                val eventDetails = "$emoji $title à $location à $time"
                val resultIntent = Intent()
                resultIntent.putExtra("eventDetails", eventDetails)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
