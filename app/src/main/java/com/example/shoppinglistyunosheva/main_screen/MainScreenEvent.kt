package com.example.shoppinglistyunosheva.main_screen

sealed class MainScreenEvent {
    object OnShowEditDialog: MainScreenEvent()
    object OnItemSave: MainScreenEvent()
}