/*
 * Copyright 2024 The Android Open Source Project
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
package com.compose.performance.stability

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tracing.trace
import com.compose.performance.R
import com.example.android.compose.recomposehighlighter.recomposeHighlighter
import java.time.LocalDate

@Composable
fun StabilityScreen(viewModel: StabilityViewModel = viewModel()) {
    // TODO Codelab task: Make items stable with strong skipping mode and annotation to prevent recomposing
    val items by viewModel.items.collectAsState()

    Box {
        Column {
            // TODO Codelab task: make LocalDate stable to prevent recomposing with each change
            LatestChange(viewModel.latestDateChange)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .recomposeHighlighter(),
                contentPadding = PaddingValues(bottom = 72.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    StabilityItemRow(
                        item = item,
                        onChecked = { viewModel.checkItem(item.id, it) },
                        onRemoveClicked = { viewModel.removeItem(item.id) }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { viewModel.addItem() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .testTag("fab")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_item)
            )
        }
    }
}

@Composable
fun LatestChange(today: LocalDate) = trace("latest_change") {
    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier.recomposeHighlighter()
    ) {
        Text(
            text = stringResource(R.string.latest_change_was, today),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun StabilityItemRow(
    item: StabilityItem,
    modifier: Modifier = Modifier,
    onChecked: (checked: Boolean) -> Unit,
    onRemoveClicked: () -> Unit
) = trace("item_row") {
    Box(modifier = modifier.recomposeHighlighter()) {
        val (rowTonalElevation, iconBg) = when (item.type) {
            StabilityItemType.REFERENCE -> 4.dp to MaterialTheme.colorScheme.primary
            StabilityItemType.EQUALITY -> 0.dp to MaterialTheme.colorScheme.tertiary
        }

        ListItem(
            tonalElevation = rowTonalElevation,
            headlineContent = { Text(item.name) },
            leadingContent = {
                Text(
                    text = item.type.name.take(3),
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconBg, CircleShape)
                        .wrapContentHeight(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            overlineContent = {
                Text("instance ${System.identityHashCode(item)}")
            },
            trailingContent = {
                Row {
                    Checkbox(checked = item.checked, onCheckedChange = onChecked)
                    IconButton(onClick = onRemoveClicked) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Delete),
                            contentDescription = stringResource(R.string.remove)
                        )
                    }
                }
            }
        )

        if (item.checked) {
            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
        }
    }
}
