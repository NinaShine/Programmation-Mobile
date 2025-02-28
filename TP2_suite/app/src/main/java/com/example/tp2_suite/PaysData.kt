package com.example.tp2_suite.data
import com.example.tp2_suite.Pays
import com.example.tp2_suite.R


object PaysData {
    val paysList = listOf(
        Pays("France", "Paris", 67000000, R.drawable.france, "Europe", "Europe de l'Ouest", "Euro (€)", "Français"),
        Pays("États-Unis", "Washington D.C.", 331000000, R.drawable.usa, "Amérique", "Amérique du Nord", "Dollar américain ($)", "Anglais"),
        Pays("Japon", "Tokyo", 126000000, R.drawable.japan, "Asie", "Asie de l'Est", "Yen japonais (¥)", "Japonais"),
        Pays("Allemagne", "Berlin", 83000000, R.drawable.germany, "Europe", "Europe de l'Ouest", "Euro (€)", "Allemand"),
        Pays("Italie", "Rome", 60000000, R.drawable.italy, "Europe", "Europe du Sud", "Euro (€)", "Italien")
    )
}
