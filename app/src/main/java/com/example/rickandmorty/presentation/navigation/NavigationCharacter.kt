package com.example.rickandmorty.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.presentation.character_detail.DetailCharacterRootScreen

import com.example.rickandmorty.presentation.character_list.ListCharacterRootScreen
import kotlin.reflect.typeOf


@ExperimentalMaterial3Api
@Composable
fun NavigationCharacter(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController, startDestination = CharacterList) {
        composable<CharacterList> {
            ListCharacterRootScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onClickListItem = { character ->
                    navController.navigate(CharacterDetail(characterId = character.id))
                }
            )
        }

        composable<CharacterDetail>() {
            DetailCharacterRootScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }


}