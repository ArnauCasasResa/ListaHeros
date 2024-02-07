package com.example.lista.model

data class Hero(
    val appearance: Appearance,
    val biography: Biography,
    val id: Int,
    val images: Images,
    val name: String,
    val powerstats: Powerstats
)