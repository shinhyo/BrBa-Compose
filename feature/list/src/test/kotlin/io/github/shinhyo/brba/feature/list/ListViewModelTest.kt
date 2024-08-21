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
import io.github.shinhyo.brba.core.model.BrbaDeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.testing.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
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

    override fun onBefore() {
        coEvery {
            charactersRepository.getCharacterList()
        } returns flowOf(
            listOf(
                BrbaCharacter(
                    charId = 7166,
                    name = "Michael Owen",
                    birthday = "semper",
                    img = "vel",
                    status = "elementum",
                    nickname = "Arthur Gates",
                    portrayed = "natoque",
                    category = listOf(),
                    description = "adipiscing",
                ),
                BrbaCharacter(
                    charId = 3906,
                    name = "Christine Yang",
                    birthday = "finibus",
                    img = "nunc",
                    status = "maiestatis",
                    nickname = "Carey Prince",
                    portrayed = "atqui",
                    category = listOf(),
                    description = "dicta",
                ),
            ),
        )

        coEvery {
            charactersRepository.getDatabaseList()
        } returns flowOf(
            listOf(
                BrbaCharacter(
                    charId = 3906,
                    name = "Christine Yang",
                    birthday = "finibus",
                    img = "nunc",
                    nickname = "Carey Prince",
                    isFavorite = true,
                ),
            ),
        )

        coEvery {
            deviceRepository.deviceData
        } returns flowOf(
            BrbaDeviceData(
                themeMode = BrbaThemeMode.Light,
            ),
        )

        coEvery {
            deviceRepository.setThemeMode(any())
        } returns Unit

        super.onBefore()
    }

    @Test
    fun `when the ListViewModel is created, it should fetch character list and device data`() =
        runTest {
            val expectedCharactersSize = 2;
            val expectedThemeMode = BrbaThemeMode.Light

            viewModel.uiState.test {
                val uiState = awaitItem()
                assertTrue(uiState is ListUiState.Success)
                assertEquals(
                    expectedCharactersSize,
                    (uiState as ListUiState.Success).characters.size,
                )
                assertEquals(expectedThemeMode, uiState.themeMode)
            }
        }

    @Test
    fun `when the user clicks on a character to toggle favorite, the updateFavoriteUseCase should be called`() =
        runTest {

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

            viewModel.onFavoriteClick(character)

            coVerify { updateFavoriteUseCase(character) }
        }

    @Test
    fun `when the user clicks to change the theme, the theme mode should be updated in the device repository`() =
        runTest {
            val currentThemeMode = BrbaThemeMode.Light

            viewModel.onChangeThemeClick(currentThemeMode)

            coVerify { deviceRepository.setThemeMode(BrbaThemeMode.Dark) }
        }

    @Test
    fun `when the user clicks to change the theme from dark to light, the theme mode should be updated in the device repository`() =
        runTest {
            val currentThemeMode = BrbaThemeMode.Dark

            viewModel.onChangeThemeClick(currentThemeMode)

            coVerify { deviceRepository.setThemeMode(BrbaThemeMode.Light) }
        }

    @Test
    fun `when the user clicks to change the theme from system, an IllegalArgumentException should be thrown`() =
        runTest {
            val currentThemeMode = BrbaThemeMode.System

            assertThrows(
                IllegalArgumentException::class.java,
            ) {
                viewModel.onChangeThemeClick(currentThemeMode)
            }
        }

}
