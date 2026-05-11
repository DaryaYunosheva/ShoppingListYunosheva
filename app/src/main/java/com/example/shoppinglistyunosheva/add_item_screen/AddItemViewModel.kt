package com.example.shoppinglistyunosheva.add_item_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistyunosheva.data.AddItem
import com.example.shoppinglistyunosheva.data.AddItemRepository
import com.example.shoppinglistyunosheva.data.ShoppingListItem
import com.example.shoppinglistyunosheva.dialog.DialogController
import com.example.shoppinglistyunosheva.dialog.DialogEvent
import com.example.shoppinglistyunosheva.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repository: AddItemRepository,
    savedStateHandle: SavedStateHandle //инициализируется уже в активити, для доступа к аргументам
): ViewModel(), DialogController {
    var itemsList: Flow<List<AddItem>>? = null
    var addItem : AddItem ?= null
    var shoppingListItem: ShoppingListItem? = null
    var listId: Int = -1

    private val _uiEvent = Channel<UiEvent>() //передатчик событий, используем только в этом классе
    val uiEvent = _uiEvent.receiveAsFlow() //получатель

    init {
        listId = savedStateHandle.get<String>("listId")?.toInt()!!
        itemsList = repository.getAllItemsById(listId)
        viewModelScope.launch {
            shoppingListItem = repository.getListItemById(listId)
        }
    }

    var itemText = mutableStateOf("")
        private set

    override var dialogTitle = mutableStateOf("Название товара")
        private set

    override var editTableText = mutableStateOf("")
        private set

    override var openDialog = mutableStateOf(false)
        private set

    override var showEditableText = mutableStateOf(true)
        private set

    fun onEvent(event: AddItemEvent){
        when (event){
            is AddItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if (listId == -1) return@launch
                    if (addItem!=null){
                        if (addItem!!.name.isEmpty()){
                            sendUiEvent(UiEvent.ShowSnackBar("Название не должно быть пустым!"))
                            return@launch
                        }
                    }
                    else{
                        if (itemText.value.isEmpty()){
                            sendUiEvent(UiEvent.ShowSnackBar("Название не должно быть пустым!"))
                            return@launch
                        }
                    }
                    repository.insertItem(
                        AddItem(
                            addItem?.id,
                            addItem?.name ?: itemText.value,
                            addItem?.isCheck ?: false,
                            listId
                        )
                    )
                    itemText.value = ""
                    addItem = null
                }
                updateShoppingListCount()
            }
            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                openDialog.value = true
                editTableText.value = addItem?.name ?: ""
            }
            is AddItemEvent.OnTextChange -> {
                itemText.value = event.text
            }
            is AddItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                    updateShoppingListCount()
                }
            }
            is AddItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertItem(event.item)
                }
                updateShoppingListCount()
            }
        }

    }


    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editTableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                openDialog.value = false
                addItem = addItem?.copy(name = editTableText.value)
                editTableText.value = ""
                onEvent(AddItemEvent.OnItemSave)
            }
            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }
        }
    }

    private fun updateShoppingListCount(){ //перезаписываем счетчики ShoppingListItem
        viewModelScope.launch {
            itemsList?.collect {list ->
                var counter = 0
                list.forEach { item ->
                    if(item.isCheck)counter++
                }
                shoppingListItem?.copy(
                    allItemsCount = list.size,
                    allSelectedItemsCount = counter
                )?.let {shItem ->
                    repository.insertItem(
                        shItem
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}