package com.example.lista.api

import com.example.lista.model.Hero

class Repository {

    val apiInterface = interfaceApi.create()
    val daoInterface = CharacterApplication.database.characterDao()
    suspend fun getAllCharacters() = apiInterface.getHeros()
    suspend fun getOneCharacter(id:Int) = apiInterface.getHero(id)

    suspend fun saveAsFavourite(character: Hero)=daoInterface.addCharacter(character)
    suspend fun deleteFavourite(character: Hero)=daoInterface.deleteCharacter(character)
    suspend fun isFavourite(character: Hero)=daoInterface.getAllCharacterById(character.id).isNotEmpty()
    suspend fun getFavourites()=daoInterface.getAllCharacters()
}