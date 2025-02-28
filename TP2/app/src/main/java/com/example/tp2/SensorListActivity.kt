package com.example.tp2

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SensorListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_list)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorInfoList = sensorList.map { sensor ->
            SensorInfo(
                name = sensor.name,
                type = sensor.stringType ?: "Type inconnu",
                vendor = sensor.vendor ?: "Inconnu",
                version = sensor.version
            )
        }

        val listView: ListView = findViewById(R.id.sensor_list_view)
        val adapter = SensorAdapter(this, sensorInfoList)
        listView.adapter = adapter

        // Bouton Retour
        val btnBack: Button = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
