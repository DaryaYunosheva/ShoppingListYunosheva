package com.example.shoppinglistyunosheva.main_screen

import com.example.shoppinglistyunosheva.R
import com.example.shoppinglistyunosheva.utils.Routes

//сгруппировали объекты
sealed class BottomNavItem(
    val title: String,
    val iconId: Int,
    val route: String
){
    object ListItem: BottomNavItem("Списки", R.drawable.list_icon, Routes.SHOPPING_LIST)
    object NoteItem: BottomNavItem("Заметки", R.drawable.note_icon, Routes.NOTE_LIST)
    object AboutItem: BottomNavItem("О приложении", R.drawable.about_icon, Routes.ABOUT)
    object SettingsItem: BottomNavItem("Настройки", R.drawable.settings_icon, Routes.SETTINGS)
}