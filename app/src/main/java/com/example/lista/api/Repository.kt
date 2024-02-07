package com.example.lista.api

class Repository {

    val apiInterface = interfaceApi.create()

    suspend fun getAllCharacters() = apiInterface.getHeros()
}