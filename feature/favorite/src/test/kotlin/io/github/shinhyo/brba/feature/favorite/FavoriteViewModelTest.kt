/*
 * Copyright 2024 shinhyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import app.cash.turbine.test
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.testing.BaseViewModelTest
import io.github.shinhyo.brba.feature.favorate.FavoriteUiState
import io.github.shinhyo.brba.feature.favorate.FavoriteViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest : BaseViewModelTest<FavoriteViewModel>() {

    override fun makeViewModel(): FavoriteViewModel = FavoriteViewModel(
        getFavoriteListUseCase = getFavoriteListUseCase,
        updateFavoriteUseCase = updateFavoriteUseCase,
    )

    @Test
    fun `Given ViewModel is initialized, When observed, Then initial state should be Loading`() =
        runTest {
            // When
            val initialState = viewModel.uiState.value

            // Then
            assertTrue(initialState is FavoriteUiState.Loading)
        }

    @Test
    fun `Given favorites exist, When uiState is observed, Then it should emit Success with data`() =
        runTest {
            // Given
            val testData = listOf(
                BrbaCharacter(
                    charId = 2770,
                    name = "Annabelle Molina",
                    birthday = "iaculis",
                    img = "ullamcorper",
                    status = "netus",
                    nickname = "Lavern Jones",
                    portrayed = "epicuri",
                    category = listOf(),
                    description = "oratio",
                ),
            )
            coEvery { charactersRepository.getDatabaseList() } returns flowOf(testData)
            val viewModel = makeViewModel()

            // Then
            viewModel.uiState.test {
                val uiState = awaitItem()
                assertTrue(uiState is FavoriteUiState.Success)
                assertEquals(testData, (uiState as FavoriteUiState.Success).list)
            }
        }

    @Test
    fun `Given getFavoriteListUseCase throws an exception, When uiState is observed, Then it should emit Error`() =
        runTest {
            // Given
            coEvery { charactersRepository.getDatabaseList() } returns flow { throw RuntimeException() }

            // When
            viewModel = makeViewModel()

            // Then
            viewModel.uiState.test {
                val uiState = awaitItem()
                assertTrue(uiState is FavoriteUiState.Error)
            }
        }

    @Test
    fun `Given no favorites exist, When uiState is observed, Then it should emit Empty`() =
        runTest {
            // Given
            coEvery { charactersRepository.getDatabaseList() } returns flowOf(emptyList())

            // When
            viewModel = makeViewModel()

            // Then
            viewModel.uiState.test {
                val uiState = awaitItem()
                assertTrue(uiState is FavoriteUiState.Empty)
            }
        }

    @Test
    fun `Given a character, When onFavoriteClick is called, Then updateFavoriteUseCase should be invoked`() =
        runTest {
            // Given
            coEvery {
                charactersRepository.updateFavorite(any())
            } returns flowOf(true)

            val character = BrbaCharacter(
                charId = 3906,
                name = "Christine Yang",
                birthday = "finibus",
                img = "nunc",
                nickname = "Carey Prince",
                isFavorite = false,
            )

            // When
            viewModel.onFavoriteClick(character)

            // Then
            coVerify { updateFavoriteUseCase(character) }
        }
}