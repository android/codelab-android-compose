package com.compose.compose_performance_workshop.stability.end

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tracing.trace

@Composable
fun StabilityScreen1_end(viewModel: StabilityViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()
    Box {
        LazyColumn(Modifier.fillMaxSize()) {
            items(items.list, key = { it.id }) { item ->
                val modifier = remember(
                    viewModel,
                    item
                ) { Modifier.clickable { viewModel.removeItem(item.id) } }
                StabilityItemRow(
                    item = item,
                    modifier = modifier
                )
            }
        }

        FloatingActionButton(
            onClick = { viewModel.addItem() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                // TODO task #3 make this composable accessible from UiAutomator
                .testTag("fab")
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
        }
    }
}

// TODO task #3 make this composable visible in trace
@Composable
fun StabilityItemRow(item: StabilityItem, modifier: Modifier = Modifier) = trace("item_row") {
    ListItem(
        headlineContent = { Text(item.name) },
        leadingContent = {
            Icon(
                painter = rememberVectorPainter(image = Icons.Default.Build),
                contentDescription = null
            )
        },
        trailingContent = {
            Checkbox(checked = false, onCheckedChange = {})
        },
        modifier = modifier
    )
}
