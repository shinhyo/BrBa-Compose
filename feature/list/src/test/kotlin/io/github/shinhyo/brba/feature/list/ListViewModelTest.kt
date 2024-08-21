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

import io.github.shinhyo.brba.core.model.BrbaDeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.testing.BaseViewModelTest
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
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
            listOf(),
        )

        coEvery {
            charactersRepository.getDatabaseList()
        } returns flowOf(
            listOf(),
        )

        coEvery {
            deviceRepository.deviceData
        } returns flowOf(
            BrbaDeviceData(
                themeMode = BrbaThemeMode.Light,
            ),
        )

        super.onBefore()
    }

    @Test
    fun test() {
    }
}