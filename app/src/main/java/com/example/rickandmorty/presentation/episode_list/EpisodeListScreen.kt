package com.example.rickandmorty.presentation.episode_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.presentation.character_detail.DetailCharacterEvents
import com.example.rickandmorty.presentation.character_list.components.LoadingScreen
import com.example.rickandmorty.presentation.episode_list.componets.EpisodeListItem
import com.example.rickandmorty.presentation.util.ObserverAsEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun EpisodeListScreen(
    uiState: EpisodeUiState,
    onAction: (EpisodeListAction) -> Unit,
    events: Flow<EpisodeListEvent>,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    ObserverAsEvents(
        events
    ) { event ->

        when (event) {
            is EpisodeListEvent.Error -> {

                scope.launch {
                    SnackbarHostState().showSnackbar(event.message.name)
                }
            }
        }
    }


    if(uiState.isLoading){
        LoadingScreen()
    }else {

        Column(
            modifier = modifier
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 8.dp,
                )
            ) {
                uiState.seasons.forEachIndexed { index, item ->

                    FilterChip(
                        selected = uiState.selectedSeasonIndex == index,
                        onClick = {
                            onAction(EpisodeListAction.onSelectedSeasonFilter(index))
                        },
                        label = {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },

                        )

                }
            }

            LazyColumn(
            ) {
                items(uiState.episodes, key = { it.id }) { episode ->
                    EpisodeListItem(
                        episode = episode,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }

}

