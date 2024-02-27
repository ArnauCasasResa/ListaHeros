    package com.example.lista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lista.ui.theme.ListaTheme
import com.example.retrofitapp.viewmodel.APIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModel by viewModels<APIViewModel>()
        setContent {
            ListaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    val bottomNavigationItems = listOf(
                        BottomNavigationScreen.Home,
                        BottomNavigationScreen.Favorite

                    )
                    Scaffold(topBar = { MyTopAppBar(myViewModel,navigationController)}, bottomBar = { MyBottomBar(navigationController,bottomNavigationItems)}) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {

                            NavHost(
                                navController = navigationController,
                                startDestination = Routes.MenuScreen.route
                            ) {
                                composable(Routes.MenuScreen.route) {MenuScreen(navigationController,myViewModel) }
                                composable(Routes.DetailScreen.route) {DetailScreen(navigationController,myViewModel)}
                                composable(Routes.FavScreen.route) {FavScreen(navigationController,myViewModel)}
                            }
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(myViewModel:APIViewModel,navController: NavController) {
    TopAppBar(
        title = { Text(text = " Comic characters",
            fontFamily = titleFont,
            fontSize = 36.sp)},
        colors= TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Search,contentDescription = null )
            }

            MySearchBar(myViewModel, navController)


        }
    )
}


    @Composable
    fun MyBottomBar(
        navigationController: NavController,
        bottomNavigationItems: List<BottomNavigationScreen>
    ) {
        BottomNavigation(backgroundColor = Color.Black, contentColor = Color.White) {
            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            bottomNavigationItems.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(item.icon, contentDescription = item.label, tint = Color.White)},
                    label = { Text(text = item.label, color = Color.White) },
                    selected = currentRoute == item.route,

                    alwaysShowLabel = false,
                    onClick = {
                        if (currentRoute != item.route) {
                            navigationController.navigate(item.route)
                        }
                    }
                )
            }
        }
    }

    val titleFont = FontFamily(
        Font(R.font.spider, FontWeight.Bold)
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MySearchBar(avm: APIViewModel, navController: NavController) {
        val searchText by avm.searchText.observeAsState("")

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        if (currentRoute == Routes.MenuScreen.route) {
            SearchBar(query = searchText,
                onQueryChange = { avm.onSearchTextChange(it) },
                onSearch = { avm.onSearchTextChange(it) },
                active = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AddCircle, contentDescription = "Mic"
                    )
                },
                placeholder = { Text("Buscar") },
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxHeight(0.1f)
                    .clip(CircleShape)
            ) {}
        }
    }
