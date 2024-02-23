package com.example.lista.myviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ScrollableViewModel:ViewModel() {
    var scrollPosition by mutableStateOf(0)
}