package com.example.rickandmorty.presentation.character_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.domain.model.Character

import com.example.rickandmorty.presentation.character_list.components.CharacterListItem
import com.example.rickandmorty.presentation.character_list.components.LoadingAppendItem
import com.example.rickandmorty.presentation.character_list.components.LoadingScreen


@Composable
fun ListCharacterScreen(
    uiState: CharacterUiState,
    items: LazyPagingItems<Character>,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (items.loadState.refresh) {
        is LoadState.Error -> {

        }
        LoadState.Loading -> {
            LoadingScreen()
        }

        is LoadState.NotLoading -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = modifier.then(
                    Modifier.padding(horizontal = 16.dp)
                )
            )
            {
                items(items.itemCount) { index ->

                    val character = items[index]

                    character?.let {
                        CharacterListItem(
                            character = it,
                            onClickItem = {
                                onAction(CharacterListAction.onClickListItem(it))
                            }
                        )
                    }


                }

                when (val appendState = items.loadState.append) {
                    is LoadState.Loading -> item { LoadingAppendItem() }
                    is LoadState.Error -> item {
                        Column {
                            Text("Error al cargar más: ${appendState.error.message}")
                            Button(onClick = { items.retry() }) {
                                Text("Reintentar")
                            }
                        }
                    }

                    else -> Unit
                }

            }
        }


    }

}