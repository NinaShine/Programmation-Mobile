package com.example.tp3_suite

import android.app.IntentService
import android.content.Intent
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.net.URL

class HtmlDownloadService : IntentService("HtmlDownloadService") {

    override fun onHandleIntent(intent: Intent?) {
        val data = intent?.getStringExtra("data") ?: return
        val user = Json.decodeFromString<UserLinks>(data)

        val urls = listOf(
            "perso.html" to user.perso,
            "linkedin.html" to user.linkedIn,
            "entreprise.html" to user.entreprise,
            "entrepriseLinkedIn.html" to user.entrepriseLinkedIn
        )

        for ((filename, urlStr) in urls) {
            try {
                if (urlStr.isBlank()) {
                    Log.w("HtmlDownloadService", "URL vide pour $filename — fichier ignoré.")
                    continue
                }

                val url = URL(urlStr)
                val content = url.readText()

                val file = File(filesDir, filename)
                file.writeText(content)

                Log.i("HtmlDownloadService", "Fichier $filename téléchargé avec succès.")
            } catch (e: Exception) {
                val file = File(filesDir, filename)
                file.writeText("Erreur lors du téléchargement : ${e.localizedMessage}")
                Log.e("HtmlDownloadService", "Erreur sur $filename avec $urlStr", e)
            }
        }
    }
}
