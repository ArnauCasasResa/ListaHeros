package com.example.lista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.sp
import com.example.lista.model.Powerstats
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import org.w3c.dom.Text

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen( navController:NavController,myViewModel: APIViewModel) {
    myViewModel.getCharacter(myViewModel.getIdx())
    val personatgeEscollit by myViewModel.character.observeAsState()
    val corazon by myViewModel.isFavorite.observeAsState(false)
    personatgeEscollit?.let { myViewModel.isFavorite(it) }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            border = BorderStroke(2.dp, Color.Transparent),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${personatgeEscollit?.id}.",
                    fontFamily = nameFont
                )
                GlideImage(
                    model = personatgeEscollit?.images?.lg,
                    contentDescription = "Character Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                personatgeEscollit?.let {
                    Text(
                        text = it.name,
                        fontFamily = nameFont,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = it.biography.fullName,
                        fontFamily = nameFont,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .clickable {
                    if (corazon == false) {
                        personatgeEscollit?.let { myViewModel.saveAsFavorite(it) }
                    } else personatgeEscollit?.let { myViewModel.removeFavorite(it) }
                }
            ) {
                if (corazon == false) {
                    Icon(
                        Icons.Filled.FavoriteBorder, contentDescription = "home",
                        tint = Color.Red, modifier = Modifier.size(60.dp)
                    )
                } else {
                    Icon(
                        Icons.Filled.Favorite, contentDescription = "home",
                        tint = Color.Red, modifier = Modifier.size(60.dp)
                    )
                }
            }
            Box {
                personatgeEscollit?.let { Barras(it.powerstats) }
            }
            Box {
                Column {
                    Text(text = "${personatgeEscollit?.biography?.fullName}, known as ${personatgeEscollit?.name}\n" +
                            "Gender:\n${personatgeEscollit?.appearance?.gender}\n" +
                            "Place of Birth:\n${personatgeEscollit?.biography?.placeOfBirth}\n" +
                            "Race:\n${personatgeEscollit?.appearance?.race}\n" +
                            "Alignment:\n${personatgeEscollit?.biography?.alignment}\n" +
                            "First appearance:\n${personatgeEscollit?.biography?.firstAppearance}\n" +
                            "Publisher:\n${personatgeEscollit?.biography?.publisher}\n",
                        )
                }
            }
        }

    }
}

@Composable
fun Barras(datos:Powerstats) {
    var barras= listOf<BarChartData.Bar>(
        BarChartData.Bar(datos.combat.toFloat(), Color.Red,"Com."),
        BarChartData.Bar(datos.durability.toFloat(), Color.Cyan,"Dur."),
        BarChartData.Bar(datos.power.toFloat(), Color.Yellow,"Pow."),
        BarChartData.Bar(datos.speed.toFloat(), Color.Green,"Spe."),
        BarChartData.Bar(datos.strength.toFloat(), Color.Magenta,"Str."),
        BarChartData.Bar(datos.intelligence.toFloat(), Color.Blue,"Int.")
    )
    BarChart(barChartData = BarChartData(
        bars = barras
    ),
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 80.dp)
            .height(300.dp),
        labelDrawer = SimpleValueDrawer(drawLocation = SimpleValueDrawer.DrawLocation.XAxis))
}
