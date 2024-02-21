package com.example.lista.api

import androidx.room.TypeConverter
import com.example.lista.model.Appearance
import com.example.lista.model.Biography
import com.example.lista.model.Images
import com.example.lista.model.Powerstats

class Converters {
    //APARIENCIA
    @TypeConverter
    fun fromAppearanceToString(appearance:Appearance):String{
        return "${appearance.gender},${appearance.race}"
    }
    @TypeConverter
    fun fromStringToAppearance(string: String): Appearance {
        val gender = string.split(",")[0]
        val race = string.split(",")[1]
        return Appearance(gender, race)
    }

    //BIOGRAFIA
    @TypeConverter
    fun fromBiographyToString(bio:Biography):String{
        return "${bio.alignment},${bio.alterEgos},${bio.firstAppearance},${bio.fullName},${bio.placeOfBirth},${bio.publisher}"
    }
    @TypeConverter
    fun fromStringToBiography(string: String): Biography {
        val alignment = string.split(",")[0]
        val alterEgos = string.split(",")[1]
        val firstAppearance = string.split(",")[2]
        val fullName = string.split(",")[3]
        val placeOfBirth = string.split(",")[4]
        val publisher = string.split(",")[5]
        return Biography(alignment, alterEgos, firstAppearance, fullName, placeOfBirth, publisher)
    }

    //IMAGES
    @TypeConverter
    fun fromImageToString(ima:Images):String{
        return "${ima.lg},${ima.md},${ima.sm},${ima.xs}"
    }
    @TypeConverter
    fun fromStringToImage(string: String): Images {
        val lg = string.split(",")[0]
        val md = string.split(",")[1]
        val sm = string.split(",")[2]
        val xs = string.split(",")[3]
        return Images(lg, md, sm, xs)
    }
    //COMBAT
    @TypeConverter
    fun fromPowerstatsToString(power:Powerstats):String{
        return "${power.combat},${power.durability},${power.intelligence},${power.power},${power.speed},${power.strength},"
    }
    @TypeConverter
    fun fromStringToPowerstats(string: String): Powerstats {
        val combat = string.split(",")[0].toInt()
        val durability = string.split(",")[1].toInt()
        val intelligence = string.split(",")[2].toInt()
        val power = string.split(",")[3].toInt()
        val speed = string.split(",")[4].toInt()
        val strength = string.split(",")[5].toInt()
        return Powerstats(combat, durability, intelligence, power, speed, strength)
    }
}