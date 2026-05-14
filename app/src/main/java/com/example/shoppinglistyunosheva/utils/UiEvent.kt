package com.example.shoppinglistyunosheva.utils

sealed class UiEvent { //события для всех экранов
    object PopBackStack: UiEvent() //вернуться назад
    data class Navigate(val route: String): UiEvent() //переключиться между экранами
    data class NavigateMain(val route: String): UiEvent()
    data class ShowSnackBar(val message: String): UiEvent()
}