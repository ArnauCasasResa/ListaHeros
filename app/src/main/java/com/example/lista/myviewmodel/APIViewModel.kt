package com.example.retrofitapp.viewmodel

import android.util.Log
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
    var character=Hero(Appearance(" "," "," ", listOf<String>()," ",listOf<String>()),
                        Biography(listOf<String>()," "," "," "," "," "," "),
                        0,
                        Images("","","","",),
                        "",
                        Powerstats(0,0,0,0,0,0)
    )
    fun getCharacters(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllCharacters()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    _characters.value = response.body()
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
                    character = response.body()!!
                    _loading.value = false
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }
}