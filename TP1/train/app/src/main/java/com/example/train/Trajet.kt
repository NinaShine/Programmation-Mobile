package com.example.train

import java.io.Serializable

data class Trajet(
    val villeDepart: String,
    val villeArrivee: String,
    val date: String,
    val heure: String? = null, // L'heure devient optionnelle
    val prix: Double // Utilisation d'un type numérique pour les calculs
) : Serializable
