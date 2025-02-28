package com.example.tp2

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProximityActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var imageView: ImageView
    private lateinit var proximityStatusText: TextView
    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity)

        imageView = findViewById(R.id.image_view)
        proximityStatusText = findViewById(R.id.text_prox)

        // Initialisation du capteur de proximité
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximitySensor == null) {
            proximityStatusText.text = "Capteur de proximité non disponible"
        } else {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Bouton retour vers MainActivity
        val boutonBack: Button = findViewById(R.id.btn_back)
        boutonBack.setOnClickListener {
            finish() // Retour à l'écran principal
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val distance = it.values[0]

            if (distance < proximitySensor!!.maximumRange.toFloat()) {
                imageView.setImageResource(R.drawable.near_image) // Image pour la main proche
                proximityStatusText.text = "Votre main est proche"
            } else {
                imageView.setImageResource(R.drawable.far_image) // Image pour la main loin
                proximityStatusText.text = "Loin"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}
