@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.rickandmorty.presentation.character_detail.viewmodel

import androidx.compose.material3.AssistChip
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isTrue
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.domain.util.NetworkError
import com.example.rickandmorty.domain.util.Result
import com.example.rickandmorty.presentation.character_detail.DetailCharacterEvents
import com.example.rickandmorty.presentation.character_detail.DetailCharacterUiState
import com.example.rickandmorty.presentation.character_detail.DetailCharacterViewModel
import com.example.rickandmorty.presentation.navigation.CharacterDetail
import com.example.rickandmorty.presentation.util.DataFake
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailCharacterViewModelTest {

    private lateinit var repository: CharacterRepository
    private lateinit var viewModelTest: DetailCharacterViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        savedStateHandle = mockk()

    }

    @AfterEach
    fun tearDown(){
        Dispatchers.resetMain()
    }


    @Test
    fun `loadingCharacter success updates uiState with character and stops loading`() = runTest {
        val character = DataFake.fakeCharacterItems[0]

        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<CharacterDetail>() } returns CharacterDetail(characterId = 1)


        coEvery { repository.getCharacters(character.id) } returns Result.Success(character)

        viewModelTest = DetailCharacterViewModel(repository, savedStateHandle)

        val job = launch {
            viewModelTest.uiState.collect{}
        }
        advanceUntilIdle()

        val state = viewModelTest.uiState.value

        Assertions.assertFalse(state.isLoading)
        Assertions.assertFalse(state.isError)
        Assertions.assertEquals(character, state.selectedCharacter)

        job.cancel()

    }

    @Test
    fun `loadingCharacter failure updates uiState with error and stops loading`() = runTest {
        val character = DataFake.fakeCharacterItems[1]

        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<CharacterDetail>() } returns CharacterDetail(characterId = 2)

        coEvery { repository.getCharacters(character.id) } returns Result.Error(NetworkError.SERVER_ERROR)

        viewModelTest = DetailCharacterViewModel(repository, savedStateHandle)


        viewModelTest.uiState.test {

            val initial = awaitItem()
            Assertions.assertFalse(initial.isLoading)
            Assertions.assertNull(initial.selectedCharacter)
            Assertions.assertFalse(initial.isError)

            val error = awaitItem()

            Assertions.assertFalse(error.isLoading)
            Assertions.assertTrue(error.isError)
            Assertions.assertNull(error.selectedCharacter)

            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `loadingCharacter failure uiState with error send error event`()= runTest{
        val character = DataFake.fakeCharacterItems[1]

        val networkError = NetworkError.SERVER_ERROR

        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<CharacterDetail>() } returns CharacterDetail(characterId = 2)

        coEvery { repository.getCharacters(character.id) } returns Result.Error(NetworkError.SERVER_ERROR)

        viewModelTest = DetailCharacterViewModel(repository, savedStateHandle)


        val job = launch {
            viewModelTest.uiState.collect {  }
        }

        advanceUntilIdle()

        val emitEvent = viewModelTest.events.first()

        Assertions.assertTrue(emitEvent is DetailCharacterEvents.Error)
        Assertions.assertEquals(networkError, (emitEvent as DetailCharacterEvents.Error).message)

        job.cancel()
    }

    @Test
    fun `loadingCharacter sets isLoading true initially and then false on success`() = runTest {
        val character = DataFake.fakeCharacterItems[1]
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<CharacterDetail>() } returns CharacterDetail(characterId = 2)

        coEvery { repository.getCharacters(character.id) } coAnswers  {
            delay(300)
            Result.Success(character)
        }

        viewModelTest = DetailCharacterViewModel(repository, savedStateHandle)

        viewModelTest.uiState.test {

            val initial = awaitItem()
            Assertions.assertFalse(initial.isLoading)
            Assertions.assertNull(initial.selectedCharacter)

            val loading = awaitItem()
            Assertions.assertTrue(loading.isLoading)

            val success = awaitItem()
            Assertions.assertFalse(success.isLoading)
            Assertions.assertEquals(character, success.selectedCharacter)

            cancelAndIgnoreRemainingEvents()
        }

    }

}