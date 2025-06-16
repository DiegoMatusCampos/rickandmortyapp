@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.rickandmorty.presentation.episode_list.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.example.rickandmorty.domain.repository.EpisodeRepository
import com.example.rickandmorty.domain.util.NetworkError
import com.example.rickandmorty.domain.util.Result
import com.example.rickandmorty.presentation.character_detail.DetailCharacterEvents
import com.example.rickandmorty.presentation.episode_list.EpisodeListEvent
import com.example.rickandmorty.presentation.episode_list.EpisodeViewModel
import com.example.rickandmorty.presentation.episode_list.componets.EpisodeListItem
import com.example.rickandmorty.presentation.navigation.CharacterDetail
import com.example.rickandmorty.presentation.navigation.EpisodeList
import com.example.rickandmorty.presentation.util.DataFake
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

class EpisodeViewModelTest {

    private lateinit var repository: EpisodeRepository
    private lateinit var viewModel: EpisodeViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        savedStateHandle = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun `loadingEpisode success update UiState with episodes list and stop loading`() = runTest {
        val listEpisode = DataFake.fakeEpisodesItems
        val idsListEpisode = DataFake.fakeEpisodesItems.map { it.id }

        coEvery { repository.getEpisodes(idsListEpisode) } coAnswers {
            delay(300)
            Result.Success(listEpisode)
        }

        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<EpisodeList>() } returns EpisodeList(
            episodesIds = idsListEpisode
        )

        viewModel = EpisodeViewModel(repository, savedStateHandle)

        viewModel.uiState.test {
            val initial = awaitItem()
            val loading = awaitItem()
            val success = awaitItem()

            Assertions.assertFalse(initial.isLoading)
            Assertions.assertTrue(loading.isLoading)
            Assertions.assertEquals(listEpisode, success.episodes)

            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `loadingEpisode failure update UiState with error and stop loading`() = runTest {
        val listEpisode = DataFake.fakeEpisodesItems
        val idsListEpisode = DataFake.fakeEpisodesItems.map { it.id }

        val networkError = NetworkError.SERVER_ERROR

        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<EpisodeList>() } returns EpisodeList(
            episodesIds = idsListEpisode
        )

        coEvery { repository.getEpisodes(idsListEpisode) } returns Result.Error(NetworkError.SERVER_ERROR)

        viewModel = EpisodeViewModel(repository, savedStateHandle)

        viewModel.uiState.test {
            val initial = awaitItem()
            Assertions.assertFalse(initial.isLoading)
            Assertions.assertTrue(initial.episodes.isEmpty())
            Assertions.assertTrue(initial.seasons.isEmpty())
            Assertions.assertFalse(initial.isError)

            val error = awaitItem()

            Assertions.assertFalse(error.isLoading)
            Assertions.assertTrue(error.isError)
            Assertions.assertTrue(initial.episodes.isEmpty())
            Assertions.assertTrue(initial.seasons.isEmpty())

            viewModel.events.test {
                val initial = awaitItem()

                Assertions.assertTrue(initial is EpisodeListEvent.Error)
                Assertions.assertEquals(networkError, (initial as EpisodeListEvent.Error).message)

            }

            cancelAndIgnoreRemainingEvents()

        }
    }

}