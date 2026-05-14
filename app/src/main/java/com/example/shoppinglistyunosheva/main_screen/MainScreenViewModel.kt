package com.example.shoppinglistyunosheva.main_screen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistyunosheva.data.ShoppingListItem
import com.example.shoppinglistyunosheva.data.ShoppingListRepository
import com.example.shoppinglistyunosheva.dialog.DialogController
import com.example.shoppinglistyunosheva.dialog.DialogEvent
import com.example.shoppinglistyunosheva.utils.Routes
import com.example.shoppinglistyunosheva.utils.UiEvent
import com.example.shoppinglistyunosheva.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository
): ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>() //передатчик событий, используем только в этом классе
    val uiEvent = _uiEvent.receiveAsFlow() //получатель


    override var dialogTitle = mutableStateOf("Название списка")
        private set

    override var editTableText = mutableStateOf("")
        private set

    override var openDialog = mutableStateOf(false)
        private set

    override var showEditableText = mutableStateOf(true)
        private set

    var showFAB = mutableStateOf(true)
        private set

    fun onEvent(event: MainScreenEvent){
        when (event){
            is MainScreenEvent.OnItemSave -> { //сохранение нового элемента
                if (editTableText.value.isBlank()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editTableText.value,
                            getCurrentTime(),
                             0,
                            0
                        )
                    )
                }
            }

            is MainScreenEvent.OnShowEditDialog -> {
                openDialog.value = true

            }

            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                if (event.route == Routes.ABOUT || event.route == Routes.SETTINGS){
                    showFAB.value = false
                }
                else {
                    showFAB.value = true
                }
            }
            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(event.route))
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
                onEvent(MainScreenEvent.OnItemSave)
                openDialog.value = false
                editTableText.value = ""
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