package com.example.rickandmorty.presentation.character_list.viewmodel

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.presentation.character_list.CharacterViewModel
import com.example.rickandmorty.presentation.util.DataFake.fakeCharacterItems
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {


    private lateinit var repository: CharacterRepository
    private lateinit var viewModelTest: CharacterViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        repository = mockk()

    }

    @AfterEach
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `items flow emits empty paging data when repository fails`() = runTest {

        every { repository.getAllCharacters() } returns flow{
            throw IOException("Error red")
        }

        val differ = AsyncPagingDataDiffer(
            diffCallback = ItemDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher
        )

        viewModelTest = CharacterViewModel(repository)

        val job = launch {
            viewModelTest.characters.collectLatest {

                differ.submitData(it)

            }
        }

        advanceUntilIdle()

        Assertions.assertTrue(differ.snapshot().isEmpty())


        job.cancel()
    }

    @Test
    fun `items flow emits expected data`() = runTest {


        val pagingData = PagingData.from(fakeCharacterItems)

        every { repository.getAllCharacters() } returns flowOf(pagingData)

        val differ = AsyncPagingDataDiffer(
            diffCallback = ItemDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher
        )


        viewModelTest = CharacterViewModel(repository)

        viewModelTest.characters.test {
            val data = awaitItem()
            differ.submitData(data)
        }

        val characterExpected = Character(
            name = "Morty",
            specie = "Human",
            status = "Alive",
            gender = "Male",
            origin = "Eart",
            lastLocation = "Desconocida",
            episodes = listOf(1,2,3,4,5,6,7),
            image = "imagen",
            id = 2
        )

        assertThat(2).isEqualTo(differ.snapshot().size)
        assertThat(characterExpected.name).isEqualTo(differ.snapshot()[1]?.name)
        assertThat(characterExpected.specie).isEqualTo(differ.snapshot()[1]?.specie)
        assertThat(characterExpected.status).isEqualTo(differ.snapshot()[1]?.status)
        assertThat(characterExpected.gender).isEqualTo(differ.snapshot()[1]?.gender)
        assertThat(characterExpected.origin).isEqualTo(differ.snapshot()[1]?.origin)
        assertThat(characterExpected.episodes).isEqualTo(differ.snapshot()[1]?.episodes)
        assertThat(characterExpected.image).isEqualTo(differ.snapshot()[1]?.image)
        assertThat(characterExpected.id).isEqualTo(differ.snapshot()[1]?.id)


    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem
    }

    private class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) = Unit
        override fun onRemoved(position: Int, count: Int) = Unit
        override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
    }

}