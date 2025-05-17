package com.example.rickandmorty.presentation.character_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.rickandmorty.presentation.character_list.components.CharacterListItem


@Composable
fun ListCharacterScreen(
    uiState: CharacterUiState,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
) {


    val listState = rememberLazyListState()

    listState.lastScrolledForward

    if (uiState.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        }
    } else {
        LazyColumn(
           state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.then(
                Modifier.padding(horizontal = 16.dp)
            )


        )
        {
            items(uiState.characters) { character ->
                CharacterListItem(
                    character = character,
                    onClickItem = {
                        onAction(CharacterListAction.onClickListItem(character))
                    }
                )
            }

        }
    }

}