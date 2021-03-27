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
package io.github.shinhyo.brba.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.data.Character
import io.github.shinhyo.brba.data.CharactersRepository
import io.github.shinhyo.brba.data.local.AppDatabase
import io.github.shinhyo.brba.data.local.CharacterEntity
import io.github.shinhyo.brba.data.local.updateFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ListViewModel
@Inject constructor(
    private val db: AppDatabase,
    private val repository: CharactersRepository,
) : ViewModel() {

    private val _db = db.characterDao().getAll()
    private val _response = MutableStateFlow(emptyList<Character>())

    private val _list = MutableStateFlow(emptyList<Character>())
    val list get() = _list.asStateFlow()

    init {
        getCharacterList()

        combine(_response, _db) { list: List<Character>, db: List<CharacterEntity> ->
            arrayListOf<Character>().apply {
                list.forEach {
                    add(
                        it.copy(
                            favorite = db.find { i -> it.charId == i.charId }?.favorite ?: false
                        )
                    )
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .onEach { _list.value = it }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    private fun getCharacterList() {
        val random = Random(Calendar.getInstance().timeInMillis)
        repository.getCharacter()
            .map { it.map { c -> c.copy(ratio = random.nextInt(4).let { r -> 1.4f + r * 0.15f }) } }
            .map { _response.value = it }
            .flowOn(Dispatchers.Default)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun upsertFavorite(character: Character) {
        viewModelScope.launch { updateFavorite(db, character) }
    }
}
