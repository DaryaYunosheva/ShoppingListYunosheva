package com.example.shoppinglistyunosheva.main_screen

import android.annotation.SuppressLint
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistyunosheva.R
import com.example.shoppinglistyunosheva.dialog.MainDialog
import com.example.shoppinglistyunosheva.navigation.NavigationGraph
import com.example.shoppinglistyunosheva.ui.theme.BlueLight


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavHostController: NavHostController,//через него переходим на экраны additem noteitem
    viewModel: MainScreenViewModel = hiltViewModel() //для этого экрана есть свой viewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {

            BottomNav(navController)
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MainScreenEvent.OnShowEditDialog)
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
            mainNavHostController.navigate(route) //onNavigate
        }
        MainDialog(viewModel)

    }
}