package com.example.lista.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.lista.model.Hero


@Dao
interface CharacterDao {
    @Query("SELECT * FROM CharacterEntity")
    suspend fun getAllCharacters():MutableList<Hero>
    @Query("SELECT * FROM CharacterEntity where id = :characterId")
    suspend fun getAllCharacterById(characterId:Int):MutableList<Hero>
    @Insert
    suspend fun addCharacter(character: Hero)
    @Delete
    suspend fun deleteCharacter(character: Hero)
}