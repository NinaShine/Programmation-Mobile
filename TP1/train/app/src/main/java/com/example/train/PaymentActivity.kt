package com.example.train

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.material.textfield.TextInputEditText

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val confirmPaymentButton = findViewById<Button>(R.id.confirmPaymentButton)
        val retourButton = findViewById<Button>(R.id.retour)

        val cardNumber = findViewById<TextInputEditText>(R.id.cardNumber)
        val expiryDate = findViewById<TextInputEditText>(R.id.expiryDate)
        val cvv = findViewById<TextInputEditText>(R.id.cvv)


        // ✅ Lorsqu'on clique sur "Finaliser la commande"
        confirmPaymentButton.setOnClickListener {
            if (validateInputs(cardNumber.text.toString(), expiryDate.text.toString(), cvv.text.toString())) {
                val intent = Intent(this, TicketQRCodeActivity::class.java)
                startActivity(intent)
            }
        }

        // ✅ Bouton Retour
        retourButton.setOnClickListener {
            val intent = Intent(this, SeatSelectionActivity::class.java)
            startActivity(intent)
        }
    }

    // ✅ Validation des champs de paiement
    private fun validateInputs(cardNumber: String, expiryDate: String, cvv: String): Boolean {
        return when {
            cardNumber.length != 16 -> {
                showToast("Le numéro de carte doit contenir 16 chiffres")
                false
            }
            !expiryDate.matches(Regex("(0[1-9]|1[0-2])/[0-9]{2}")) -> {
                showToast("Format de la date invalide (MM/YY)")
                false
            }
            cvv.length != 3 -> {
                showToast("Le CVV doit contenir 3 chiffres")
                false
            }
            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
