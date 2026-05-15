package com.example.shoppinglistyunosheva.note_list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglistyunosheva.R
import com.example.shoppinglistyunosheva.ui.theme.DarkText
import com.example.shoppinglistyunosheva.ui.theme.LightText

@Preview(showBackground = true)
@Composable
fun UiNoteListItem(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, start = 3.dp, end = 3.dp)
            .clickable(){

            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp)
                        .weight(1f),
                    text = "Note 1",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText

                    ),

                )
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp),
                    text = "23/23/2342 13:34",
                    style = TextStyle(
                        color = LightText,
                        fontSize = 12.sp
                    )

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 10.dp, bottom = 10.dp)
                        .weight(1f),
                    text = "fnskvjskdn s skjdnkncsdkjfk s kdncjsjsnd sjdknfs fjne enrfueuirfieirnfu enbiurfeuiufneriei nernfienrinfienrnfiern ernfineir",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = LightText //?
                )

                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_icon),
                        contentDescription = "delete",
                        tint = DarkText
                    )
                }
            }
        }
    }
}