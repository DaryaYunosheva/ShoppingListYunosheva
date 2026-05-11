package com.example.shoppinglistyunosheva.shopping_list_screen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistyunosheva.data.ShoppingListItem
import com.example.shoppinglistyunosheva.data.ShoppingListRepository
import com.example.shoppinglistyunosheva.dialog.DialogEvent
import com.example.shoppinglistyunosheva.dialog.DialogController
import com.example.shoppinglistyunosheva.utils.UiEvent
import com.example.shoppinglistyunosheva.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
): ViewModel(), DialogController {

    val list = repository.getAllItems()//не используем корутины, потому что используем класс flow

    private val _uiEvent = Channel<UiEvent>() //передатчик событий, используем только в этом классе
    val uiEvent = _uiEvent.receiveAsFlow() //получатель
    private var listItem: ShoppingListItem? = null

    override var dialogTitle = mutableStateOf("")
        private set //геттер публичный, а сеттер нет, только из этого класса

    override var editTableText = mutableStateOf("")
        private set

    override var openDialog = mutableStateOf(false)
        private set

    override var showEditableText = mutableStateOf(false)
        private set


    fun onEvent(event: ShoppingListEvent){
        when (event){
            is ShoppingListEvent.OnItemSave -> { //только редактирование, тк fab в другом composable
                if (editTableText.value.isBlank()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            editTableText.value,
                            listItem?.time ?: getCurrentTime(),
                            listItem?.allItemsCount ?: 0,
                            listItem?.allSelectedItemsCount ?: 0
                        )
                    )
                }
            }
            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route)) //отправляем событие composable, тк нет доступа к навигации
            }
            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editTableText.value = listItem?.name ?: ""
                dialogTitle.value = "Название списка:"
                showEditableText.value = true

            }
            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = "Удалить этот список?"
                showEditableText.value = false
            }

        }
    }

    override fun onDialogEvent(event: DialogEvent){
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value){
                    onEvent(ShoppingListEvent.OnItemSave)
                }
                else{
                    viewModelScope.launch {
                        repository.deleteItem(listItem!!)
                    }
                }
                openDialog.value = false
            }
            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}