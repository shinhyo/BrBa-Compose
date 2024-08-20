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
package io.github.shinhyo.brba.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme

@Composable
fun BrbaChips(
    modifier: Modifier = Modifier,
    chipList: List<String>,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        chipList.forEach { chip ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .background(MaterialTheme.colorScheme.onTertiary)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Text(
                    text = chip,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        BrbaChips(
            chipList = listOf(
                "Breaking Bad1",
                "Breaking Bad3",
                "Breaking Bad3",
                "Breaking Bad3",
                "Breaking Bad3",
                "Breaking Bad3",
                "Breaking Bad5",
                "Better Call Saul6",
            ),
        )
    }
}