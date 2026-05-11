package com.example.shoppinglistyunosheva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglistyunosheva.main_screen.MainScreen
import com.example.shoppinglistyunosheva.navigation.MainNavigationGraph
import com.example.shoppinglistyunosheva.ui.theme.ShoppingListYunoshevaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint //пометили, что в этом активити, можем использовать инжект
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListYunoshevaTheme {

                MainNavigationGraph()

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListYunoshevaTheme {
        Greeting("Android")
    }
}