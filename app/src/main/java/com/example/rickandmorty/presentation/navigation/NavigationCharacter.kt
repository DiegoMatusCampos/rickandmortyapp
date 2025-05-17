package com.example.rickandmorty.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.character_detail.DetailCharacterScreen
import com.example.rickandmorty.presentation.character_list.CharacterListAction
import com.example.rickandmorty.presentation.character_list.CharacterListEvents
import com.example.rickandmorty.presentation.character_list.CharacterViewModel
import com.example.rickandmorty.presentation.character_list.ListCharacterScreen
import com.example.rickandmorty.presentation.util.ObserverAsEvents


@ExperimentalMaterial3Api
@Composable
fun NavigationCharacter(
    viewModel: CharacterViewModel = hiltViewModel<CharacterViewModel>(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserverAsEvents(events = viewModel.events) { event ->
        when(event){
            is CharacterListEvents.Error -> {

            }
        }

    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(

        topBar = {
            TopAppBar(
                onBack = {
                    navController.navigateUp()
                },
                scrollBehavior = scrollBehavior,
                canNavigateBack = navController.previousBackStackEntry != null
            )
        },

        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {innerPadding ->
        NavHost(navController = navController, startDestination = CharacterList) {
            composable<CharacterList> {
                ListCharacterScreen(
                    uiState = uiState,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action){
                            is CharacterListAction.onClickListItem -> {
                                navController.navigate(route = CharacterDetail)
                            }
                        }
                    },
                    modifier = modifier.padding(innerPadding)
                )
            }

            composable<CharacterDetail> {
                DetailCharacterScreen(uiState = uiState,
                    modifier.padding(innerPadding)
                )
            }
        }
    }


}