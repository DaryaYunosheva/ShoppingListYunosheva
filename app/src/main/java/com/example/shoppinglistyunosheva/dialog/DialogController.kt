package com.example.shoppinglistyunosheva.dialog

import androidx.compose.runtime.MutableState

interface DialogController {
    val dialogTitle: MutableState<String>
    val editTableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>

    fun onDialogEvent(event: DialogEvent)
}