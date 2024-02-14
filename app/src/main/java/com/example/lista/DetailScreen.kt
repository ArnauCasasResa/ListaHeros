package com.example.lista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import retrofit2.Response

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen(navController: NavController, myViewModel: APIViewModel){
    val characters: ListaHeros by myViewModel.characters.observeAsState(ListaHeros())
    val apiInterface = interfaceApi.create()
    myViewModel.getCharacter(myViewModel.getIdx())
    val personatgeEscollit by myViewModel.character.observeAsState()

    Card(
        border = BorderStroke(2.dp, Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),) {
            Text(
                text = "${personatgeEscollit?.id}.",
                style = MaterialTheme.typography.bodyLarge
            )
            GlideImage(
                model = personatgeEscollit?.images?.lg,
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            Column() {
                personatgeEscollit?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
                    )
                }

            }

        }
    }
}