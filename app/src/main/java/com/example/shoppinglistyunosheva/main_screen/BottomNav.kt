package com.example.shoppinglistyunosheva.main_screen

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppinglistyunosheva.ui.theme.BlueLight
import com.example.shoppinglistyunosheva.ui.theme.GrayDark


@Composable
fun BottomNav(
    navController: NavHostController
) {
    val listItems = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.NoteItem,
        BottomNavItem.AboutItem,
        BottomNavItem.SettingsItem
    )

    BottomAppBar(containerColor = Color.White
        ) {
        listItems.forEach { bottomNavItem ->
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route //тк это состояние, то при каждом изменении currentRoute, все будет перерисовываться
            NavigationBarItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                    navController.navigate(bottomNavItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = bottomNavItem.iconId
                        ),
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(text = bottomNavItem.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BlueLight,
                    selectedTextColor = BlueLight,
                    unselectedIconColor = GrayDark,
                    unselectedTextColor = GrayDark
                ),
                alwaysShowLabel = false //только если selected равен true, тогда будет показываться заголовок
            )
        }
    }
}