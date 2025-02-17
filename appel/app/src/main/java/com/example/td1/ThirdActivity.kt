package com.example.td1

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import com.example.td1.MainActivity





class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        // Récupération du numéro de téléphone envoyé depuis SecondActivity
        val phoneNumber = intent.getStringExtra("PHONE")
        val textPhone = findViewById<TextView>(R.id.textPhone)
        textPhone.text = "Numéro : $phoneNumber"

        val btnCall = findViewById<Button>(R.id.btnCall)
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL) // Intent Implicite pour composer le numéro
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }

        val btnRetourMain = findViewById<Button>(R.id.btnRetourMain)
        btnRetourMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() 
        }
    }
}