package com.example.tp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.widget.BaseAdapter

class SensorAdapter(private val context: Context, private val sensorList: List<SensorInfo>) : BaseAdapter() {

    override fun getCount(): Int = sensorList.size
    override fun getItem(position: Int): Any = sensorList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.sensor_item, parent, false)

        val sensor = sensorList[position]

        val sensorName: TextView = view.findViewById(R.id.sensor_name)
        val sensorType: TextView = view.findViewById(R.id.sensor_type)
        val sensorVendor: TextView = view.findViewById(R.id.sensor_vendor)
        val sensorVersion: TextView = view.findViewById(R.id.sensor_version)

        sensorName.text = sensor.name
        sensorType.text = "Type: ${sensor.type}"
        sensorVendor.text = "Vendeur: ${sensor.vendor}"
        sensorVersion.text = "Version: ${sensor.version}"

        return view
    }
}
