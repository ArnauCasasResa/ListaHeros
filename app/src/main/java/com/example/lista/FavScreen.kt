package com.example.lista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lista.model.Hero
import com.example.lista.model.ListaHeros
import com.example.retrofitapp.viewmodel.APIViewModel

@Composable
fun FavScreen(navController: NavController,myViewModel:APIViewModel){
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val characters: MutableList<Hero> by myViewModel.favCharacters.observeAsState(ListaHeros())
    myViewModel.getFavorites()
    if(showLoading){
        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(300.dp))
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
    else{
        if (characters.isNotEmpty()){
            LazyColumn() {
                items(characters) {
                    FavCharacterItem(character = it,navController,myViewModel)
                }
            }
        }else{
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "No hay personajes en favoritos.",
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray)
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavCharacterItem(character: Hero, navController: NavController,myViewModel: APIViewModel) {
    Card(
        border = BorderStroke(2.dp, Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                myViewModel.setIdx(character.id)
                myViewModel.isFavorite(character)
                navController.navigate(Routes.DetailScreen.route)
            }
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),) {
            Text(
                text = "${character.id }.  ",
                style = MaterialTheme.typography.bodyLarge
            )
            GlideImage(
                model = character.images.lg,
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            Column() {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
                )

            }

        }
    }
}