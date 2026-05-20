package com.example.shoppinglistyunosheva.new_note_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.shoppinglistyunosheva.R
import com.example.shoppinglistyunosheva.ui.theme.BlueLight
import com.example.shoppinglistyunosheva.ui.theme.DarkText
import com.example.shoppinglistyunosheva.ui.theme.GrayLight
import com.example.shoppinglistyunosheva.ui.theme.LightText
import com.example.shoppinglistyunosheva.utils.UiEvent

@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                else -> {}
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = GrayLight)
    ){
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier.weight(1f).padding(top = 7.dp),
                        value = viewModel.title,
                        onValueChange = {text ->
                            viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
                        },
                        label = {
                            Text(
                                text = "Заголовок",
                                fontSize = 14.sp,
                                color = LightText
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = BlueLight,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                    )

                    IconButton(
                        onClick = {
                            viewModel.onEvent(NewNoteEvent.OnSave)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.save_icon),
                            contentDescription = "save",
                            tint = DarkText)
                    }

                }

                TextField(
                    value = viewModel.description,
                    onValueChange = { text ->
                        viewModel.onEvent(NewNoteEvent.OnDescriptionChange(text))
                    },
                    label = {
                        Text(text = "Описание...",
                            fontSize = 14.sp,
                            color = LightText)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = DarkText
                    )
                )
            }
        }
    }

}