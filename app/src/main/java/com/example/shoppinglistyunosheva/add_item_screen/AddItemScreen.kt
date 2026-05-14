package com.example.shoppinglistyunosheva.add_item_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.shoppinglistyunosheva.R
import com.example.shoppinglistyunosheva.dialog.MainDialog
import com.example.shoppinglistyunosheva.ui.theme.BlueLight
import com.example.shoppinglistyunosheva.ui.theme.DarkText
import com.example.shoppinglistyunosheva.ui.theme.GrayDark
import com.example.shoppinglistyunosheva.ui.theme.GrayLight
import com.example.shoppinglistyunosheva.utils.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val snackbarHostState = remember{ SnackbarHostState() }
    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent){
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }
        }
    }

    Scaffold(snackbarHost = {SnackbarHost(snackbarHostState)}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = viewModel.itemText.value,
                        onValueChange = {text ->
                            viewModel.onEvent(AddItemEvent.OnTextChange(text))
                        },
                        label = {
                            Text(text = "Новый товар",
                                fontSize = 12.sp)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = BlueLight,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White

                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = DarkText
                        ),
                        singleLine = true
                    )

                    IconButton(
                        modifier = Modifier.background(Color.White),
                        onClick = {
                            viewModel.onEvent(AddItemEvent.OnItemSave)
                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "add"
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                .fillMaxSize()
                .padding(top = 3.dp, start = 5.dp, end = 5.dp)
            ) {
                if (itemsList != null){
                    items(itemsList.value){item ->
                        UiAddItem(item = item,
                            onEvent = { event ->
                                viewModel.onEvent(event)
                            }
                        )
                    }
                }
            }
        }
        MainDialog(viewModel)
        if (itemsList?.value?.isEmpty() == true){
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "Пусто",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = GrayDark)
        }
    }
}