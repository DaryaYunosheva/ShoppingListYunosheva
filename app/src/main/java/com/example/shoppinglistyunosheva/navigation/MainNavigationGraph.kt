package com.example.shoppinglistyunosheva.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistyunosheva.add_item_screen.AddItemScreen
import com.example.shoppinglistyunosheva.main_screen.MainScreen
import com.example.shoppinglistyunosheva.new_note_screen.NewNoteScreen
import com.example.shoppinglistyunosheva.utils.Routes


@Composable
fun MainNavigationGraph (){

    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.MAIN_SCREEN){
        composable(Routes.ADD_ITEM + "/{listId}"){
            AddItemScreen()
        }
        composable(Routes.NEW_NOTE){
            NewNoteScreen()
        }
        composable(Routes.MAIN_SCREEN){
            MainScreen(navController)//передаем контроллер потому что в mainscreen отвечаем за все события
        }
    }
}