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
package com.compose.performance.accelerate

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.tracing.trace
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HeavyScreenViewModel : ViewModel() {
    private var _items = MutableStateFlow(emptyList<HeavyItem>())
    val items = _items.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            _items.value = generateItems(1000)
        }
    }
}

@Stable
data class HeavyItem(
    val id: Int,
    val published: Instant,
    val description: String,
    val url: String,
    val tags: List<String>
)

/**
 * Simple method formatting an [Instant] to a local time with time zone.
 */
fun Instant.format(timeZone: TimeZone): String = trace("PublishDate.format") {
    val dt = toLocalDateTime(timeZone)

    val day = dt.dayOfMonth.toString().padStart(2, '0')
    val month = dt.monthNumber.toString().padStart(2, '0')
    val year = dt.year.toString()
    val hh = dt.hour.toString().padStart(2, '0')
    val mm = dt.minute.toString().padStart(2, '0')

    "$day.$month.$year - $hh:$mm\n$timeZone"
}

private fun generateItems(howMany: Int) = List(howMany) { index ->
    HeavyItem(
        id = index,
        published = Clock.System.now() - stableRandom.nextInt(48 * 60).minutes,
        description = "Item $index",
        url = poolOfImageUrls[stableRandom.nextInt(poolOfImageUrls.size)],
        tags = poolOfTags.shuffled(stableRandom).take(4)
    )
}

private val poolOfTags =
    listOf("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")

private val poolOfImageUrls = listOf(
    "https://picsum.photos/id/57/2448/3264",
    "https://picsum.photos/id/36/4179/2790",
    "https://picsum.photos/id/96/4752/3168",
    "https://picsum.photos/id/180/2400/1600",
    "https://picsum.photos/id/252/5000/3281"
)

private val stableRandom = Random(0)
