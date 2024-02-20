package com.example.lista.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "CharacterEntity")
data class Hero(
    val appearance: Appearance,
    val biography: Biography,
    @PrimaryKey val id: Int,
    val images: Images,
    val name: String,
    val powerstats: Powerstats
)