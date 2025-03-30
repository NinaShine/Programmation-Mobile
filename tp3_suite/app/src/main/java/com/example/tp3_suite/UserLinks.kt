package com.example.tp3_suite

import kotlinx.serialization.Serializable

@Serializable
data class UserLinks(
    val nom: String,
    val prenom: String,
    val perso: String,
    val linkedIn: String,
    val entreprise: String,
    val entrepriseLinkedIn: String
)
