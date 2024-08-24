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
package io.github.shinhyo.brba.feature.list

import app.cash.turbine.test
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.testing.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ListViewModelTest : BaseViewModelTest<ListViewModel>() {

    override fun makeViewModel(): ListViewModel = ListViewModel(
        getCharacterListUseCase = getCharacterListUseCase,
        updateFavoriteUseCase = updateFavoriteUseCase,
        deviceRepository = deviceRepository,
    )

    @Test
    fun `Given ListViewModel is created, When it initializes, Then it should fetch character list and device data`() = runTest {
        // Given
        val expectedCharactersSize = 2
        val expectedThemeMode = BrbaThemeMode.Light

        // Then
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertTrue(uiState is ListUiState.Success)
            assertEquals(expectedCharactersSize, (uiState as ListUiState.Success).characters.size)
            assertEquals(expectedThemeMode, uiState.themeMode)
        }
    }

    @Test
    fun `Given character list fetch throws RuntimeException, When ViewModel is created, Then uiState should be Error`() = runTest {
        // Given
        coEvery {
            charactersRepository.getCharacterList()
        } returns flow { throw RuntimeException() }

        // When
        val viewModel = makeViewModel()

        // Then
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertTrue(uiState is ListUiState.Error)
        }
    }

    @Test
    fun `Given a character, When the user clicks to toggle favorite, Then updateFavoriteUseCase should be called`() = runTest {
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

    @Test
    fun `Given light theme, When the user clicks to change the theme, Then the theme mode should be updated to dark`() = runTest {
        // Given
        val currentThemeMode = BrbaThemeMode.Light

        // When
        viewModel.onChangeThemeClick(currentThemeMode)

        // Then
        coVerify { deviceRepository.setThemeMode(BrbaThemeMode.Dark) }
    }

    @Test
    fun `Given dark theme, When the user clicks to change the theme, Then the theme mode should be updated to light`() = runTest {
        // Given
        val currentThemeMode = BrbaThemeMode.Dark

        // When
        viewModel.onChangeThemeClick(currentThemeMode)

        // Then
        coVerify { deviceRepository.setThemeMode(BrbaThemeMode.Light) }
    }

    @Test
    fun `Given system theme, When the user clicks to change the theme, Then an IllegalArgumentException should be thrown`() = runTest {
        // Given
        val currentThemeMode = BrbaThemeMode.System

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            viewModel.onChangeThemeClick(currentThemeMode)
        }
    }
}