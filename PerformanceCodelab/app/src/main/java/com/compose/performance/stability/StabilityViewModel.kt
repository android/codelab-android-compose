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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

// TODO Codelab task: make this class Stable
data class StabilityItem(
    val id: Int,
    val type: StabilityItemType,
    val name: String,
    val checked: Boolean,
    val created: LocalDateTime
)

class StabilityViewModel : ViewModel() {

    private var incrementingKey = 0

    // We're using neverEqualPolicy to showcase what the UI logic does for unstable parameters with each time different instance.
    var latestDateChange by mutableStateOf<LocalDate>(LocalDate.now(), neverEqualPolicy())
        private set

    private val _items = MutableStateFlow<List<StabilityItem>>(emptyList())

    val items = _items
        .map { simulateNewInstances(it) }
        .onEach { latestDateChange = LocalDate.now() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        repeat(3) {
            addItem()
        }
    }

    fun addItem() {
        _items.value += StabilityItem(
            id = incrementingKey++,
            type = StabilityItemType.fromId(incrementingKey),
            name = sampleData.random(stableRandom),
            checked = false,
            created = LocalDateTime.now()
        )
    }

    fun removeItem(id: Int) {
        _items.value = _items.value.filterNot { it.id == id }
    }

    fun checkItem(id: Int, checked: Boolean) {
        val items = _items.value.toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index < 0) return
        items[index] = items[index].copy(checked = checked)
        _items.value = items
    }
}

enum class StabilityItemType {
    REFERENCE,
    EQUALITY;

    companion object {
        fun fromId(id: Int) = if (id % 2 == 0) REFERENCE else EQUALITY
    }
}

/**
 * We have this method to simulate when a data source provides new instance when mapping a class.
 * This can occur for remote services like Firebase, REST Api, or local services like Room or sqlDelight.
 */
private fun simulateNewInstances(items: List<StabilityItem>): List<StabilityItem> = items.map {
    if (it.type == StabilityItemType.REFERENCE) {
        // For the reference types, we recreate the class to be always a new instance
        it.copy()
    } else {
        it
    }
}

private val sampleData = LoremIpsum(500)
    .values.first()
    .split(" ")
    .map { it.trim('.', ',', '\n') }

private val stableRandom = Random(0)
