package com.example.tp2

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tp2.R

class SensorCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_check)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        val textView: TextView = findViewById(R.id.sensor_status_text)

        val unavailableSensors = mutableListOf<String>()

        if (proximitySensor == null) {
            unavailableSensors.add("❌ Capteur de proximité indisponible")
        }
        if (accelerometerSensor == null) {
            unavailableSensors.add("❌ Accéléromètre indisponible")
        }
        if (gyroscopeSensor == null) {
            unavailableSensors.add("❌ Gyroscope indisponible")
        }
        if (lightSensor == null) {
            unavailableSensors.add("❌ Capteur de lumière indisponible")
        }

        textView.text = if (unavailableSensors.isEmpty()) {
            "✅ Tous les capteurs essentiels sont disponibles"
        } else {
            unavailableSensors.joinToString("\n")
        }

        // Bouton Retour
        val btnBack: Button = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
