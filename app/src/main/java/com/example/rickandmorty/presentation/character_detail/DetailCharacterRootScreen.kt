package com.example.rickandmorty.presentation.character_detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue


@ExperimentalMaterial3Api
@Composable
fun DetailCharacterRootScreen(
    onNavigateUp: () -> Unit,
    onClickButton: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailCharacterViewModel = hiltViewModel<DetailCharacterViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState<DetailCharacterUiState>()

    DetailCharacterScreen(
        uiState = uiState,
        onAction = { action ->
            when(action){
                DetailCharacterListAction.onNavigateUp -> onNavigateUp()
                is DetailCharacterListAction.onClickShowEpisodes -> {
                    onClickButton(action.ids)
                }
            }
        },
        events = viewModel.events

    )

}