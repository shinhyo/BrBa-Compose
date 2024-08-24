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
package io.github.shinhyo.brba.core.testing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.github.shinhyo.brba.core.domain.repository.CharactersRepository
import io.github.shinhyo.brba.core.domain.repository.DeviceRepository
import io.github.shinhyo.brba.core.domain.usecase.GetCharacterListUseCase
import io.github.shinhyo.brba.core.domain.usecase.GetFavoriteListUseCase
import io.github.shinhyo.brba.core.domain.usecase.UpdateFavoriteUseCase
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.model.BrbaDeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

abstract class BaseViewModelTest<T : ViewModel> : ViewModelTest<T>() {

    protected val savedStateHandle: SavedStateHandle by lazy { mockk(relaxed = true) }

    protected val charactersRepository: CharactersRepository by lazy { mockk() }
    protected val deviceRepository: DeviceRepository by lazy { mockk() }

    protected val getCharacterListUseCase by lazy {
        GetCharacterListUseCase(
            ioDispatcher = testDispatcher,
            repo = charactersRepository,
        )
    }

    protected val updateFavoriteUseCase by lazy {
        UpdateFavoriteUseCase(
            ioDispatcher = testDispatcher,
            repo = charactersRepository,
        )
    }

    protected val getFavoriteListUseCase by lazy {
        GetFavoriteListUseCase(
            ioDispatcher = testDispatcher,
            repo = charactersRepository,
        )
    }

    override fun setDefaultMockData() {
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
    }
}