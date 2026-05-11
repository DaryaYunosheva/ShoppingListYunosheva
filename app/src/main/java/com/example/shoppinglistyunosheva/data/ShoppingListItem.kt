package com.example.shoppinglistyunosheva.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_name") //на основе этого дата-класса будет создаваться таблица базы данных
data class ShoppingListItem(
    @PrimaryKey
    val id: Int? = null,//если значения id не будет, то библиотека сама ему присвоит id уже согласно индентификаторам, что есть в бд
    val name: String,
    val time: String,
    val allItemsCount: Int,
    val allSelectedItemsCount: Int
)
