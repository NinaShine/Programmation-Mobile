package com.example.train

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.train.api.SNCFClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        val departEditText = findViewById<EditText>(R.id.departEditText)
        val arriveeEditText = findViewById<EditText>(R.id.arriveeEditText)
        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        val searchButton = findViewById<Button>(R.id.searchButton)

        dateTextView.setOnClickListener { showDatePicker(dateTextView) }

        searchButton.setOnClickListener {
            val depart = departEditText.text.toString().trim()
            val arrivee = arriveeEditText.text.toString().trim()
            val date = dateTextView.text.toString().trim()

            if (depart.isEmpty() || arrivee.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isInternetAvailable(this)) {
                Toast.makeText(this, "Aucune connexion Internet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val formattedDate = formatDateForSNCF(date)
                    val fromCode = getGareId(depart)
                    val toCode = getGareId(arrivee)

                    if (fromCode == null || toCode == null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Gare introuvable", Toast.LENGTH_LONG).show()
                        }
                        return@launch
                    }

                    Log.d("SNCF API", "Requête vers SNCF : from=$fromCode, to=$toCode, datetime=${formattedDate}T000000")

                    val results = SNCFClient.service.getTrainJourneys(
                        auth = "Basic 376bc7a6-c1f5-4b87-a220-a8394712e58a",
                        from = fromCode,
                        to = toCode,
                        datetime = "${formattedDate}T000000"
                    )

                    Log.d("SNCF API Response", results.toString())

                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@MainActivity, ResultsActivity::class.java).apply {
                            putParcelableArrayListExtra("trajets", ArrayList(results.journeys))
                        }
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("SNCF API Error", e.message ?: "Erreur inconnue")
                        Toast.makeText(
                            this@MainActivity, "Erreur API SNCF : ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }
    }

    private fun showDatePicker(dateTextView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    "%04d/%02d/%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                dateTextView.text = formattedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun formatDateForSNCF(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

        return try {
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate!!)
        } catch (e: Exception) {
            date.replace("/", "")
        }
    }

    private fun getGareId(stationName: String): String? {
        return when (stationName.lowercase(Locale.ROOT)) {
            "paris" -> "admin:fr:75056"
            "lyon" -> "admin:fr:69123"
            "marseille" -> "admin:fr:13055"
            "lille" -> "admin:fr:59350"
            "bordeaux" -> "admin:fr:33063"
            "nantes" -> "admin:fr:44109"
            "montpellier" -> "admin:fr:34172"
            "toulouse" -> "admin:fr:31555"
            "strasbourg" -> "admin:fr:67482"
            "nice" -> "admin:fr:06088"
            else -> null
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}
