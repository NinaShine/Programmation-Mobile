package com.example.tp2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem

class GeoLocationActivity : AppCompatActivity(), LocationListener {

    private lateinit var map: MapView
    private lateinit var locationManager: LocationManager
    private lateinit var tvLat: TextView
    private lateinit var tvLong: TextView
    private var userLocation: Location? = null
    private var geoPoint: GeoPoint? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Configuration d'OpenStreetMap
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        setContentView(R.layout.activity_geolocation)

        tvLat = findViewById(R.id.tvLat)
        tvLong = findViewById(R.id.tvLon)
        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)

        // Initialisation du gestionnaire de localisation
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Vérifier si le GPS est activé
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            userLocation = getLocation()
            updateMap()
        } else {
            promptEnableGPS()
        }

        // Bouton pour rafraîchir la position
        val btnRefresh: Button = findViewById(R.id.btn_refresh_location)
        btnRefresh.setOnClickListener {
            userLocation = getLocation()
            updateMap()
        }

        // Bouton retour vers MainActivity
        val btnBack: Button = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onLocationChanged(location: Location) {
        userLocation = location
        updateMap()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userLocation = getLocation()
                updateMap()
            } else {
                Toast.makeText(this, "Permission refusée !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(): Location? {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return null
        }

        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val location = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                bestLocation = location
            }
        }
        return bestLocation
    }

    private fun promptEnableGPS() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Le GPS est désactivé. Voulez-vous l’activer ?")
            .setCancelable(false)
            .setPositiveButton("Oui") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Non") { dialog, _ ->
                dialog.cancel()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun updateMap() {
        userLocation?.let {
            val latitude = it.latitude
            val longitude = it.longitude

            tvLat.text = "Latitude: $latitude"
            tvLong.text = "Longitude: $longitude"

            geoPoint = GeoPoint(latitude, longitude)
            map.controller.setCenter(geoPoint)
            map.controller.setZoom(17.0)

            val markers = ArrayList<OverlayItem>()
            val overlayItem = OverlayItem("Position actuelle", "Localisation", geoPoint)
            markers.add(overlayItem)

            val overlay = ItemizedOverlayWithFocus(applicationContext, markers, null)
            overlay.setFocusItemsOnTap(true)
            map.overlays.add(overlay)
        }
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}


}
