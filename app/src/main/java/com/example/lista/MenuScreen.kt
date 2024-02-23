package com.example.lista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lista.model.Hero
import com.example.lista.model.ListaHeros
import com.example.lista.myviewmodel.ScrollableViewModel
import com.example.retrofitapp.viewmodel.APIViewModel

@Composable
fun MenuScreen(
    navController: NavController,
    myViewModel: APIViewModel,
    myViewModelScroll: ScrollableViewModel
) {
    val showLoading: Boolean by myViewModel.loading.observeAsState(true)
    val characters: ListaHeros by myViewModel.characters.observeAsState(ListaHeros())
    myViewModel.getCharacters()

    // Variable para almacenar la posición de desplazamiento
    var scrollPosition by remember { mutableStateOf(0) }

    if (showLoading) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(270.dp))
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    } else {
        LazyColumn(
            // Restaurar la posición de desplazamiento al volver a la pantalla
            state = rememberLazyListState(scrollPosition)
        ) {
            items(characters) {
                CharacterItem(character = it, navController, myViewModel)
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(character: Hero, navController: NavController,myViewModel: APIViewModel) {
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
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = nameFont,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
val nameFont = FontFamily(
    Font(R.font.avenger, FontWeight.Bold)
)