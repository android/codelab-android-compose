package com.compose.compose_performance_workshop.derivedstate.end

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Screen1_end() {
    Box(Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        val isScrolled by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

        List_end(listState)

        // I only want this to show if the list has been scrolled
        if (isScrolled) {
            FloatingActionButton(
                onClick = {/* TODO */ },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Scroll up")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List_end(state: LazyListState) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize()
    ) {
        items(1000) { index ->
            ListItem(headlineContent = { Text(index.toString()) })
        }
    }
}