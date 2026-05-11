package com.example.shoppinglistyunosheva.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoppinglistyunosheva.about_screen.AboutScreen
import com.example.shoppinglistyunosheva.note_list_screen.NoteListScreen
import com.example.shoppinglistyunosheva.settings_screen.SettingsScreen
import com.example.shoppinglistyunosheva.shopping_list_screen.ShoppingListScreen
import com.example.shoppinglistyunosheva.utils.Routes


@Composable
fun NavigationGraph (
    navController: NavHostController,
    onNavigate: (String) -> Unit //передаем на слой выше
){


    NavHost(navController, startDestination = Routes.SHOPPING_LIST){ //по умолчанию вначале открываетсыя экран списки покупок
        composable(Routes.SHOPPING_LIST){
            ShoppingListScreen(){ route ->
                onNavigate(route)
            }
        }
        composable(Routes.NOTE_LIST){
            NoteListScreen()
        }
        composable(Routes.ABOUT){
            AboutScreen()
        }
        composable(Routes.SETTINGS){
            SettingsScreen()
        }
    }
}