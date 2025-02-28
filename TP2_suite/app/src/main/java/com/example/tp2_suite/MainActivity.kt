package com.example.tp2_suite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListePaysFragment.OnPaysSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)

        Log.d("MainActivity", "onCreate appelé")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListePaysFragment())
                .commit()
        }
    }

    override fun onPaysSelected(pays: Pays) {
        Log.d("MainActivity", "✅ Pays sélectionné : ${pays.nom}")

        val detailFragment = supportFragmentManager.findFragmentById(R.id.fragment_detail) as? DetailPaysFragment

        if (detailFragment != null) {
            Log.d("MainActivity", "✅ Mode tablette détecté")
            detailFragment.afficherDetails(pays)

            // Rendre visible le fragment en mode tablette
            val detailContainer = findViewById<View>(R.id.fragment_detail)
            detailContainer.visibility = View.VISIBLE
        } else {
            Log.d("MainActivity", "✅ Mode téléphone - Remplacement du fragment")

            val bundle = Bundle().apply {
                putParcelable("pays", pays)
            }

            val newFragment = DetailPaysFragment().apply {
                arguments = bundle
            }

            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()

            Log.d("MainActivity", "✅ Fragment DetailPaysFragment ajouté avec ${pays.nom}")
        }
    }



}
