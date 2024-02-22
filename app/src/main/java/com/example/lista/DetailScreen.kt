package com.example.lista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lista.api.interfaceApi
import com.example.lista.model.Hero
import com.example.lista.model.ListaHeros
import com.example.retrofitapp.viewmodel.APIViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen( navController:NavController,myViewModel: APIViewModel){
    myViewModel.getCharacter(myViewModel.getIdx())
    val personatgeEscollit by myViewModel.character.observeAsState()
    val corazon by myViewModel.isFavorite.observeAsState(myViewModel.character.value?.let {
        myViewModel.isFavorite(it)})
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            border = BorderStroke(2.dp, Color.Transparent),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)

        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${personatgeEscollit?.id}.",
                    style = MaterialTheme.typography.bodyLarge
                )
                GlideImage(
                    model = personatgeEscollit?.images?.lg,
                    contentDescription = "Character Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(300.dp)
                )
                personatgeEscollit?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Box(modifier = Modifier
            .clickable {
                if (corazon == false){
                    personatgeEscollit?.let { myViewModel.saveAsFavorite(it) }
                }else personatgeEscollit?.let { myViewModel.removeFavorite(it) }
                personatgeEscollit?.let { myViewModel.isFavorite(it) }
                myViewModel.character.value?.let { myViewModel.isFavorite(it) }
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    navController.navigate(Routes.MenuScreen.route)
                }

            }
        ){
            if (corazon == false){
                Icon(Icons.Filled.FavoriteBorder, contentDescription = "home")
            }else{
                Icon(Icons.Filled.Favorite, contentDescription = "home")
            }
        }
    }
}