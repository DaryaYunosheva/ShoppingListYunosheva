package com.example.shoppinglistyunosheva.shopping_list_screen

import com.example.shoppinglistyunosheva.data.ShoppingListItem

sealed class ShoppingListEvent{  //класс событий для конкретного экрана
    data class OnShowDeleteDialog(val item: ShoppingListItem): ShoppingListEvent() //событие показать окно подтверждения удаления
    data class OnShowEditDialog(val item: ShoppingListItem): ShoppingListEvent() //событие показать окно редактирования
    data class OnItemClick(val route: String): ShoppingListEvent() // событие нажатие на список
    object OnItemSave: ShoppingListEvent() // событие сохранения списка
}
