package com.example.train

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import kotlin.random.Random

class TicketQRCodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_qr)

        val qrCodeImageView = findViewById<ImageView>(R.id.qrCodeImageView)
        val ticketNumberTextView = findViewById<TextView>(R.id.ticketNumberTextView)
        val returnButton = findViewById<Button>(R.id.retour)

        // ✅ Générer un numéro de billet aléatoire
        val ticketNumber = "TICKET-" + Random.nextInt(100000, 999999)
        ticketNumberTextView.text = "Numéro de billet : $ticketNumber"

        // ✅ Générer un QR Code à partir du numéro de billet
        val bitmap = generateQRCode(ticketNumber)
        qrCodeImageView.setImageBitmap(bitmap)

        // ✅ Gestion du bouton "Retour"
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Ferme l'activité actuelle pour éviter d'empiler les pages
        }
    }

    // ✅ Fonction pour générer un QR Code
    private fun generateQRCode(text: String): Bitmap {
        val width = 500
        val height = 500
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height)
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bmp
    }
}
