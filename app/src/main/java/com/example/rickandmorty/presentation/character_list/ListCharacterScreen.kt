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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.R
import com.example.rickandmorty.domain.model.Character

import com.example.rickandmorty.presentation.character_list.components.CharacterListItem
import com.example.rickandmorty.presentation.character_list.components.LoadingAppendItem
import com.example.rickandmorty.presentation.character_list.components.LoadingScreen
import com.example.rickandmorty.presentation.navigation.TopAppBar
import com.example.rickandmorty.presentation.util.ObserverAsEvents
import com.example.rickandmorty.presentation.util.SnackbarController
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@Composable
fun ListCharacterScreen(
    items: LazyPagingItems<Character>,
    onAction: (CharacterListAction) -> Unit,
    modifier: Modifier = Modifier
) {


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserverAsEvents(SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

           val result =  snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if( result == SnackbarResult.ActionPerformed){
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost= {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        topBar = {
            TopAppBar(
                title = stringResource(R.string.characters),
                onBack = {
                    onAction(CharacterListAction.onNavigateUp)
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        when (items.loadState.refresh) {
            is LoadState.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error al cargar")
                    Button(onClick = { items.retry() }) {
                        Text("Reintentar")
                    }
                }
            }

            LoadState.Loading -> {
                LoadingScreen()
            }

           else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = modifier.padding(innerPadding).then(
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

                    when (items.loadState.append) {
                        is LoadState.NotLoading -> item {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Error al cargar")
                                Button(onClick = { items.retry() }) {
                                    Text("Reintentar")
                                }
                            }
                        }
                        is LoadState.Loading -> item { LoadingAppendItem() }
                        is LoadState.Error -> item {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.
                                fillMaxSize()
                            ) {
                                Text("Error al cargar más personajes")
                                Button(onClick = { items.retry() }) {
                                    Text("Reintentar")
                                }
                            }
                        }

                    }

                }
            }


        }
    }

}