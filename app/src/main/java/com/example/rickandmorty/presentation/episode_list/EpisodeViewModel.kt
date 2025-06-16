package com.example.rickandmorty.presentation.episode_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.rickandmorty.domain.repository.EpisodeRepository
import com.example.rickandmorty.domain.util.onError
import com.example.rickandmorty.domain.util.onSuccess
import com.example.rickandmorty.presentation.navigation.CharacterDetail
import com.example.rickandmorty.presentation.navigation.EpisodeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
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
class EpisodeViewModel @Inject constructor(
    private val repository: EpisodeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val route = savedStateHandle.toRoute<EpisodeList>()

    private val _uiState = MutableStateFlow<EpisodeUiState>(EpisodeUiState())
    val uiState: StateFlow<EpisodeUiState> = _uiState.onStart {
        loadEpisodes(route.episodesIds)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = EpisodeUiState()
    )

    private val _events = Channel<EpisodeListEvent>()
    val events: Flow<EpisodeListEvent> = _events.receiveAsFlow()


    fun loadEpisodes(ids: List<Int>, seasonIndex: Int = 0) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        repository.getEpisodes(ids).onSuccess { data ->

            val seasonsMaps = data.groupBy { episode ->
                episode.seasonNumber
            }

            val seasons = listOf<String>("Todas") + seasonsMaps.keys.map {
                "Temporada $it"
            }

            val dataFiltered = if(seasonIndex > 0) data.filter {
                it.seasonNumber == seasons[seasonIndex].takeLast(1).toInt()
            } else data

            _uiState.update {
                it.copy(
                    isError = false,
                    isLoading = false,
                    episodes = dataFiltered ,
                    seasons = seasons,
                    selectedSeasonIndex = seasonIndex
                )
            }
        }.onError { error ->
            _uiState.update {
                it.copy(
                    isError = true,
                    isLoading = false
                )
            }
            _events.send(EpisodeListEvent.Error(error))
        }


    }

    fun onAction(action: EpisodeListAction) {
        when (action) {
            is EpisodeListAction.onSelectedSeasonFilter -> {

                loadEpisodes(route.episodesIds, action.index)

            }
        }
    }

}