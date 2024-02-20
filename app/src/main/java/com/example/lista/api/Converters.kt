package com.example.lista.api

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.lista.model.Appearance

class Converters {
    @TypeConverter
    fun fromAppearanceToString(appearance:Appearance):String{
        return "${appearance.eyeColor},${appearance.hairColor},${appearance.gender},${appearance.race}"
    }
    @TypeConverter
    fun fromStringToList(string: String):List<String>{
        return string.split(",")
    }
}