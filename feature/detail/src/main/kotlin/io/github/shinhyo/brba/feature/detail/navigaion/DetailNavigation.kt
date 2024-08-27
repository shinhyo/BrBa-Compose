/*
 * Copyright 2022 shinhyo
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
package io.github.shinhyo.brba.feature.detail.navigaion

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.detail.DetailRoute
import kotlinx.serialization.Serializable

@Serializable
data class Detail(val id: Long, val image: String)

fun NavController.navigateToDetail(character: BrbaCharacter) {
    this.navigate(
        Detail(id = character.charId, image = character.img),
    )
}

context(SharedTransitionScope)
fun NavGraphBuilder.detailComposable() {
    composable<Detail> {
        DetailRoute(
            animatedVisibilityScope = this,
        )
    }
}