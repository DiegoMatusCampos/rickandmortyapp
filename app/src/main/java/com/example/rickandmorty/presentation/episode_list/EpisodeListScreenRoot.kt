package com.example.rickandmorty.presentation.episode_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.navigation.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@ExperimentalMaterial3Api
@Composable
fun EpisodeListScreenRoot(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EpisodeViewModel = hiltViewModel<EpisodeViewModel>(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.episode_title),
                onBack = onNavigateUp,
                scrollBehavior = scrollBehavior,
                canNavigateUp = true
            )
        }
    ) { innerPadding ->
        EpisodeListScreen(
            uiState = uiState,
            onAction = { action ->
                when(action){
                    is EpisodeListAction.onSelectedSeasonFilter -> {
                        viewModel.onAction(action)
                    }
                }
            },
            modifier = Modifier.padding(innerPadding),
            events = viewModel.events
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun EpisodeListScreenPreview() {
    RickAndMortyTheme {
        EpisodeListScreenRoot(
            onNavigateUp = {

            }
        )
    }
}