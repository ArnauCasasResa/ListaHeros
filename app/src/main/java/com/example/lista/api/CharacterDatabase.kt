package com.example.lista.api

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lista.model.Hero

@Database(entities = arrayOf(Hero::class), version = 1)
@TypeConverters(Converters::class)
abstract class CharacterDatabase:RoomDatabase() {
    abstract fun characterDao():CharacterDao
}