package com.example.lista

import com.example.lista.model.Hero

sealed class Routes(val route: String)
{
    object MenuScreen:Routes("menu_screen" )
    object DetailScreen:Routes("detail_screen" )
    object FavScreen:Routes("fav_screen" )
}