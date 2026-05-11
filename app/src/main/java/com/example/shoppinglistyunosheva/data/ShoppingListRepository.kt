package com.example.shoppinglistyunosheva.data


import kotlinx.coroutines.flow.Flow


interface ShoppingListRepository {// просто абстрактно указать функции
    suspend fun insertItem(item: ShoppingListItem)
    suspend fun deleteItem(item: ShoppingListItem)
    fun getAllItems(): Flow<List<ShoppingListItem>>
}