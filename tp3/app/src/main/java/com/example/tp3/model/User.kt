package com.example.tp3.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "nom")
    val nom: String,

    @ColumnInfo(name = "prenom")
    val prenom: String,

    @ColumnInfo(name = "dateNaissance")
    val dateNaissance: String,

    @ColumnInfo(name = "telephone")
    val telephone: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "centresInteret")
    val centresInteret: String
)
