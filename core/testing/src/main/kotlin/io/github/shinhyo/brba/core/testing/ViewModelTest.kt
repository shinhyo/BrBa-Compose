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

import androidx.lifecycle.ViewModel
import io.github.shinhyo.brba.core.testing.rule.MainDispatcherRule
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class ViewModelTest<T : ViewModel> {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val testDispatcher by lazy { mainDispatcherRule.testDispatcher }

    protected lateinit var viewModel: T

    abstract fun makeViewModel(): T

    @Before
    open fun onBefore() {
        viewModel = makeViewModel()
    }

    @After
    open fun onAfter() {
    }
}