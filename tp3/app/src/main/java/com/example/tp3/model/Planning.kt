package com.example.tp3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plannings")
data class Planning(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val login: String,
    val date: String,
    val creneau1: String,
    val creneau2: String,
    val creneau3: String,
    val creneau4: String
)
