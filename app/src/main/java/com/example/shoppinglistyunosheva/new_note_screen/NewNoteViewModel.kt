package com.example.shoppinglistyunosheva.new_note_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistyunosheva.data.NoteItem
import com.example.shoppinglistyunosheva.data.NoteRepository
import com.example.shoppinglistyunosheva.utils.UiEvent
import com.example.shoppinglistyunosheva.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>() //передатчик событий, используем только в этом классе
    val uiEvent = _uiEvent.receiveAsFlow() //получатель

    private var noteId = -1
    private var noteItem: NoteItem? = null

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    init {
        noteId = savedStateHandle.get<String>("noteId")?.toInt()!!
        if (noteId!=-1){
            viewModelScope.launch {
                repository.getNoteItemById(noteId).let { noteItem ->
                   title = noteItem.title
                   description = noteItem.description
                   this@NewNoteViewModel.noteItem = noteItem
                }
            }
        }
    }

    fun onEvent(event: NewNoteEvent){
        when(event){
            is NewNoteEvent.OnSave -> {
                viewModelScope.launch {
                    repository.insertItem(
                        NoteItem(
                            noteItem?.id,
                            title,
                            description,
                            time = getCurrentTime()
                        )
                    )
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
            is NewNoteEvent.OnTitleChange -> {
                title = event.title
            }
            is NewNoteEvent.OnDescriptionChange -> {
                description = event.description

            }
        }

    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}