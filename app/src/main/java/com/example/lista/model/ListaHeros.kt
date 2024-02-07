package com.example.lista.model

data class ListaHeros (
    val characters: List<Hero>,
    val currentPage: Int,
    val pageSize: Int,
    val total: Int
)