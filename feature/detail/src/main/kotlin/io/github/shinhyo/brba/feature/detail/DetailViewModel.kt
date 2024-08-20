/*
 * Copyright 2021 shinhyo
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
package io.github.shinhyo.brba.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.core.common.result.Result
import io.github.shinhyo.brba.core.common.result.asResult
import io.github.shinhyo.brba.core.domain.usecase.GetCharacterUseCase
import io.github.shinhyo.brba.core.domain.usecase.UpdateFavoriteUseCase
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.detail.navigaion.DetailArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class DetailInfoState {
    data object Loading : DetailInfoState()
    data class Success(val character: BrbaCharacter) : DetailInfoState()
    data class Error(val exception: Throwable) : DetailInfoState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
) : ViewModel() {

    private val args: DetailArgs by lazy { DetailArgs(savedStateHandle = savedStateHandle) }

    private val _imageState = MutableStateFlow(args.id to args.image)
    val imageState = _imageState.asStateFlow()

    val infoState = getCharacterUseCase(id = args.id)
        .asResult()
        .map {
            when (it) {
                is Result.Loading -> DetailInfoState.Loading
                is Result.Success -> {
                    val character: BrbaCharacter = it.data
                    DetailInfoState.Success(character = it.data)
                }

                is Result.Error -> DetailInfoState.Error(exception = it.exception)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailInfoState.Loading,
        )

    fun updateFavorite(character: BrbaCharacter) {
        updateFavoriteUseCase(character)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}