package com.example.lista.api

import com.example.lista.model.Hero
import com.example.lista.model.ListaHeros
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface interfaceApi {

    @GET("all.json")
    suspend fun getHeros(): Response<ListaHeros>

    @GET("id/{id}.json")
    suspend fun getHero(@Path("id")heroId:Int):Response<Hero>
    companion object {
        val BASE_URL = "https://akabab.github.io/superhero-api/api/"
        fun create(): interfaceApi {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(interfaceApi::class.java)
        }
    }
}