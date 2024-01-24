package com.compose.compose_performance_workshop.stability.end

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

@Stable
data class StabilityItem(
    val id: Int,
    val name: String,
    val created: LocalDateTime = LocalDateTime.now()
)

@Stable
data class ListHolder(
    val list: List<StabilityItem> = listOf()
)

class StabilityViewModel: ViewModel() {
    private val sampleData = LoremIpsum(500)
        .values.first().replace("\n", "").split(" ")

    private var incrementingKey = 0

    private val _items = MutableStateFlow(ListHolder())
    val items = _items.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = ListHolder()
    )

    init {
        repeat(3) {
            addItem()
        }
    }

    fun addItem() {
        val item = StabilityItem(
            id = incrementingKey++,
            name = sampleData.random()
        )

        _items.value = ListHolder(_items.value.list + listOf(item))
    }

    fun removeItem(id: Int) {
        val items = _items.value.list.toMutableList()
        val index = items.indexOfFirst { it.id == id } ?: return
        items.removeAt(index)

        _items.value = ListHolder(items.toList())
    }
}