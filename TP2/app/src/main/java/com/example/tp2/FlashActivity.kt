package com.example.tp2

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FlashActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var isFlashOn = false
    private var lastShakeTime: Long = 0

    private lateinit var flashValue: TextView
    private lateinit var texteFlash: TextView
    private lateinit var btnFlash: Button
    private lateinit var flashIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)

        flashValue = findViewById(R.id.forceValue)
        texteFlash = findViewById(R.id.text_flash)
        flashIcon = findViewById(R.id.flash_icon)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId!!, false)
            updateFlashUI()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            Toast.makeText(this, "Erreur d'accès au flash", Toast.LENGTH_SHORT).show()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            Toast.makeText(this, "Accéléromètre non disponible", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        btnFlash = findViewById(R.id.btn_back)
        btnFlash.setOnClickListener {
            finish()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            val gForce = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat() / SensorManager.GRAVITY_EARTH
            flashValue.text = String.format("Force: %.2f", gForce)

            val now = System.currentTimeMillis()
            val elapsedTime = now - lastShakeTime

            if (elapsedTime > 1000 && gForce > 3) {
                lastShakeTime = now
                toggleFlash()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun toggleFlash() {
        try {
            cameraId?.let {
                if (isFlashOn) {
                    cameraManager.setTorchMode(it, false)
                    isFlashOn = false
                } else {
                    cameraManager.setTorchMode(it, true)
                    isFlashOn = true
                }
                updateFlashUI()
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun updateFlashUI() {
        if (isFlashOn) {
            texteFlash.text = "Flash ON"
            flashIcon.setImageResource(R.drawable.ic_flash_on)
        } else {
            texteFlash.text = "Flash OFF"
            flashIcon.setImageResource(R.drawable.ic_flash_off)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        try {
            cameraId?.let {
                cameraManager.setTorchMode(it, false)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        isFlashOn = false
        updateFlashUI()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }
}
