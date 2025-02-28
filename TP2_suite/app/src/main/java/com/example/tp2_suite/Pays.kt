package com.example.tp2_suite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pays(
    val nom: String,
    val capitale: String,
    val population: Int,
    val drapeauResId: Int,
    val region: String,
    val sousRegion: String,
    val monnaie: String,
    val langue: String
) : Parcelable
