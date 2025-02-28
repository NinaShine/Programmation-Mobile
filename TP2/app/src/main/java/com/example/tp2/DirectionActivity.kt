package com.example.tp2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.atan2
import kotlin.math.roundToInt

class DirectionActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var rotationVectorSensor: Sensor? = null

    private lateinit var directionValue: TextView
    private lateinit var imageLeft: ImageView
    private lateinit var imageRight: ImageView
    private lateinit var imageUp: ImageView
    private lateinit var imageDown: ImageView
    private lateinit var boutonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction)

        // Récupération des éléments UI
        directionValue = findViewById(R.id.text_direction)
        imageLeft = findViewById(R.id.image_left)
        imageRight = findViewById(R.id.image_right)
        imageUp = findViewById(R.id.image_up)
        imageDown = findViewById(R.id.image_down)

        boutonBack = findViewById(R.id.bouton_back)
        boutonBack.setOnClickListener {
            finish()
        }

        // Initialisation du capteur de rotation
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        if (rotationVectorSensor == null) {
            directionValue.text = "Aucun capteur de rotation détecté"
        }
    }

    override fun onResume() {
        super.onResume()
        rotationVectorSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

                val orientationValues = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientationValues)

                val azimuth = Math.toDegrees(orientationValues[0].toDouble()).roundToInt()
                updateDirection(azimuth)
            }
        }
    }

    private fun updateDirection(azimuth: Int) {
        var direction = "Neutre"

        when {
            azimuth in -45..45 -> {
                direction = "Haut"
                showDirection(imageUp)
            }
            azimuth in 45..135 -> {
                direction = "Droite"
                showDirection(imageRight)
            }
            azimuth in -135..-45 -> {
                direction = "Gauche"
                showDirection(imageLeft)
            }
            else -> {
                direction = "Bas"
                showDirection(imageDown)
            }
        }

        directionValue.text = "Direction : $direction"
    }

    private fun showDirection(activeImage: ImageView) {
        imageLeft.visibility = View.INVISIBLE
        imageRight.visibility = View.INVISIBLE
        imageUp.visibility = View.INVISIBLE
        imageDown.visibility = View.INVISIBLE

        activeImage.visibility = View.VISIBLE
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Pas utilisé ici
    }
}
