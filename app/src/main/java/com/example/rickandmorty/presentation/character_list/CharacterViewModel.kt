package com.example.rickandmorty.presentation.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.domain.util.onError
import com.example.rickandmorty.domain.util.onSucces
import com.example.rickandmorty.presentation.util.SnackbarAction
import com.example.rickandmorty.presentation.util.SnackbarController
import com.example.rickandmorty.presentation.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import jakarta.inject.Inject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> = _uiState.onStart {
        loadingCharacters()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CharacterUiState()
        )

    val characters = repository.getAllCharacters().cachedIn(viewModelScope)


    private val _events = Channel<CharacterListEvents>()
    val events = _events.receiveAsFlow()

    fun onShowSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = message
                )
            )
        }
    }


    private fun loadingCharacters() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
        }
    }


}