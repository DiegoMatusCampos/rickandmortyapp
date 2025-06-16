package com.example.rickandmorty.presentation.character_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.domain.util.onError
import com.example.rickandmorty.domain.util.onSuccess
import com.example.rickandmorty.presentation.navigation.CharacterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val route =  savedStateHandle.toRoute<CharacterDetail>()

    private val _uiState = MutableStateFlow(DetailCharacterUiState())
    val uiState: StateFlow<DetailCharacterUiState> = _uiState.onStart {
        loadingCharacter()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DetailCharacterUiState()
        )

    private val _events = Channel<DetailCharacterEvents>()
    val events = _events.receiveAsFlow()

    private fun loadingCharacter() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getCharacters(route.characterId).onSuccess { data ->
                _uiState.update {
                    it.copy(
                        selectedCharacter = data,
                        isLoading = false
                    )
                }
            }.onError { error ->
                _uiState.update {
                    it.copy(
                        isLoading =  false,
                        isError = true
                    )
                }

                _events.send(DetailCharacterEvents.Error(error))
            }
        }


    }
}