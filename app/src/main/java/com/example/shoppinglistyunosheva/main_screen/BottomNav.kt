package com.example.shoppinglistyunosheva.main_screen

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.shoppinglistyunosheva.ui.theme.BlueLight
import com.example.shoppinglistyunosheva.ui.theme.GrayDark


@Composable
fun BottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
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

            NavigationBarItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                    onNavigate(bottomNavItem.route)
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