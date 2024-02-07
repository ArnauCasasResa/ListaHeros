package com.example.lista

sealed class Routes(val route: String)
{
    object MenuScreen:Routes("menu_Screen" )
    object Pantalla2:Routes("pantalla2" )
    object Pantalla3:Routes("pantalla3" )
}