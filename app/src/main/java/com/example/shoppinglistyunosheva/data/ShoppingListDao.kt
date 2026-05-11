package com.example.shoppinglistyunosheva.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao // для доступа к данным (дата аксес обджект)
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingListItem)
    @Delete
    suspend fun deleteItem(item: ShoppingListItem)
    @Query("DELETE FROM add_item WHERE listId = :listId") //для удаления товаров принадлежащих списку
    suspend fun deleteAddItems(listId: Int)
    @Query("SELECT * FROM shop_list_name")
    fun getAllItems(): Flow<List<ShoppingListItem>> //флоу будет следить за изменениями в таблице - выдавать обновленный список каждый раз
    @Transaction
    suspend fun deleteShoppingList(item: ShoppingListItem){
        deleteAddItems(item.id!!)
        deleteItem(item)
    }
}
