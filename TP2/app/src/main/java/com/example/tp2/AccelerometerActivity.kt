package com.example.tp2

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccelerometerActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var layout: LinearLayout
    private lateinit var accelerationText: TextView
    private var currentColor: Int = Color.GREEN // Couleur actuelle

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        layout = findViewById(R.id.accelerometer_layout)
        accelerationText = findViewById(R.id.acceleration_text)

        // Initialisation du SensorManager et de l'accéléromètre
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Vérifier si l'accéléromètre est disponible
        if (accelerometer == null) {
            layout.setBackgroundColor(Color.GRAY) // Couleur grise si aucun capteur n'est détecté
            accelerationText.text = "Aucun capteur d'accéléromètre détecté."
        }

        // Bouton Retour
        val btnBack: Button = findViewById(R.id.bouton_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]

                val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()

                // Mise à jour du texte d'affichage
                accelerationText.text = String.format("X: %.2f Y: %.2f Z: %.2f\nTotal: %.2f", x, y, z, acceleration)

                // Définition des seuils
                val newColor = when {
                    acceleration < 8.8 -> Color.GREEN // Faible mouvement
                    acceleration  < 12.0 -> Color.BLACK // Mouvement modéré
                    else -> Color.RED // Mouvement fort
                }

                if (newColor != currentColor) {
                    animateBackgroundColorChange(currentColor, newColor)
                    currentColor = newColor
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Pas utilisé ici
    }

    private fun animateBackgroundColorChange(fromColor: Int, toColor: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        colorAnimation.duration = 500 // Durée de transition en ms
        colorAnimation.addUpdateListener { animator ->
            layout.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
    }
}
