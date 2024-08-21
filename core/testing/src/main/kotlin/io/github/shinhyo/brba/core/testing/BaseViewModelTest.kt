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
import io.github.shinhyo.brba.core.domain.usecase.UpdateFavoriteUseCase
import io.mockk.mockk

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
}