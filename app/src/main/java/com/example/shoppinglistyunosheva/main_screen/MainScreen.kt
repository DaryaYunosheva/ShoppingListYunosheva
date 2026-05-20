package com.example.shoppinglistyunosheva.main_screen

import android.annotation.SuppressLint
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistyunosheva.R
import com.example.shoppinglistyunosheva.dialog.MainDialog
import com.example.shoppinglistyunosheva.navigation.NavigationGraph
import com.example.shoppinglistyunosheva.ui.theme.BlueLight
import com.example.shoppinglistyunosheva.utils.Routes
import com.example.shoppinglistyunosheva.utils.UiEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavHostController: NavHostController,//через него переходим на экраны additem noteitem
    viewModel: MainScreenViewModel = hiltViewModel() //для этого экрана есть свой viewModel
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route //тк это состояние, то при каждом изменении currentRoute, все будет перерисовываться

    LaunchedEffect(key1 = true) { //принимаем события экрана и передаем во ViewModel
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UiEvent.NavigateMain -> {
                    mainNavHostController.navigate(uiEvent.route)
                }
                is UiEvent.Navigate -> {
                    navController.navigate(uiEvent.route)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        bottomBar = {

            BottomNav(currentRoute){route ->
                viewModel.onEvent(MainScreenEvent.Navigate(route))
            }
    },
        floatingActionButton = {
            if (viewModel.showFAB.value) FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MainScreenEvent.OnNewItemClick(currentRoute ?: Routes.SHOPPING_LIST))
                },
                containerColor = BlueLight
            ) {
                Icon(painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Add",
                    tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        NavigationGraph(navController){ route ->
           viewModel.onEvent(MainScreenEvent.NavigateMain(route))
        }
        MainDialog(viewModel)

    }
}