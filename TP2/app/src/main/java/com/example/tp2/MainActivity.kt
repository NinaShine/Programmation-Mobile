package com.example.tp2

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp2.SensorCheckActivity
import com.example.tp2.ui.theme.TP2Theme

class MainActivity : ComponentActivity() {

    private lateinit var btnShakeFlash : Button
    private lateinit var btnProximity : Button
    private lateinit var btn_back : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        /*
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorNames = sensorList.map { it.name }

        val listView: ListView = findViewById(R.id.sensor_list_view)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorNames)
        listView.adapter = adapter*/
        val btnShowSensors: Button = findViewById(R.id.btn_show_sensors)
        val btnCheckSensors: Button = findViewById(R.id.btn_check_sensors)
        val btnAccelerometer: Button = findViewById(R.id.btn_accelerometer)
        val btnDirection: Button = findViewById(R.id.btn_direction)
        btnShakeFlash = findViewById(R.id.btn_shake_flash)
        btnProximity = findViewById(R.id.btn_proximity)
        btn_back = findViewById(R.id.btn_back)


        btnShowSensors.setOnClickListener {
            val intent = Intent(this, SensorListActivity::class.java)
            startActivity(intent)
        }

        btnCheckSensors.setOnClickListener {
            val intent = Intent(this, SensorCheckActivity::class.java)
            startActivity(intent)
        }

        btnAccelerometer.setOnClickListener {
            val intent = Intent(this, AccelerometerActivity::class.java)
            startActivity(intent)
        }
        btnDirection.setOnClickListener {
            val intent = Intent(this, DirectionActivity::class.java)
            startActivity(intent)
        }
        btnShakeFlash.setOnClickListener {
            val intent = Intent(this, FlashActivity::class.java)
            startActivity(intent)
        }

        btnProximity.setOnClickListener {
            val intent = Intent(this, ProximityActivity::class.java)
            startActivity(intent)
        }
        btn_back.setOnClickListener {
            val intent = Intent(this, GeoLocationActivity::class.java)
            startActivity(intent)
        }

    }
}
