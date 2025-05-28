package com.example.rickandmorty.presentation.character_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.presentation.util.ObserverAsEvents

@ExperimentalMaterial3Api
@Composable
fun ListCharacterRootScreen(
    onNavigateUp: () -> Unit,
    onClickListItem: (Character) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel<CharacterViewModel>()

) {
    val  characters = viewModel.characters.collectAsLazyPagingItems()



    ListCharacterScreen(
            items = characters,
            onAction = { action ->
                when(action) {
                    is CharacterListAction.onClickListItem -> {
                        onClickListItem(action.character)
                    }
                    CharacterListAction.onNavigateUp -> onNavigateUp
                    is CharacterListAction.onShowSnackbarClick -> {  ->
                        viewModel.onShowSnackbar(action.message)
                    }
                }
            },
        )
    }