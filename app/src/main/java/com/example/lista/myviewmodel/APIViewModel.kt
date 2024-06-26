package com.example.retrofitapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lista.api.Repository
import com.example.lista.model.Appearance
import com.example.lista.model.Biography
import com.example.lista.model.Hero
import com.example.lista.model.Images
import com.example.lista.model.ListaHeros
import com.example.lista.model.Powerstats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APIViewModel: ViewModel() {
    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _characters = MutableLiveData<ListaHeros>()
    val characters = _characters
    private var _favCharacters=MutableLiveData<MutableList<Hero>>()
    var favCharacters=_favCharacters
    private var _isFavorite=MutableLiveData(false)
    var isFavorite=_isFavorite
    var character=MutableLiveData<Hero>()
    private var id=0
    fun getCharacters(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllCharacters()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    _characters.value = response.body()
                    _charactersFromAPI.value=_characters.value
                    _loading.value = false
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }
    fun getCharacter(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOneCharacter(id)
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    character.value = response.body()!!
                    _loading.value = false
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }
    fun getFavorites(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFavourites()
            withContext(Dispatchers.Main) {
                _favCharacters.value = response
                _loading.value = false
            }
        }
    }
    fun saveAsFavorite(character:Hero){
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsFavourite(character)
            _isFavorite.postValue(true)
        }
    }
    fun removeFavorite(character:Hero){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavourite(character)
            _isFavorite.postValue(false)
        }
    }
    fun isFavorite(character:Hero){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isFavourite(character)
            withContext(Dispatchers.Main){
                _isFavorite.value=response
            }
        }
    }
    fun getIdx():Int{
        return this.id
    }
    fun setIdx(num:Int){
        this.id=num
    }

    private val _charactersFromAPI = MutableLiveData<ListaHeros>()
    private val charactersFromAPI = _charactersFromAPI
    private val _searchText = MutableLiveData("")
    val searchText = _searchText
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        val filteredCharacters = charactersFromAPI.value?.filter { it.name.lowercase().contains(text.lowercase()) }
        if (text.isEmpty()) characters.value = charactersFromAPI.value
        else characters.value = ListaHeros().apply { addAll(filteredCharacters ?: emptyList()) }
    }

}

