/*
 * Copyright 2021 The Android Open Source Project
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

package com.example.jetnews.data.interests

import com.example.jetnews.utils.addOrRemove
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

typealias TopicsMap = Map<String, List<String>>

/**
 * Implementation of InterestRepository that returns a hardcoded list of
 * topics, people and publications synchronously.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class InterestsRepository {

    /**
     * Get relevant topics to the user.
     */
    val topics by lazy {
        mapOf(
            "Android" to listOf("Jetpack Compose", "Kotlin", "Jetpack"),
            "Programming" to listOf("Kotlin", "Declarative UIs", "Java"),
            "Technology" to listOf("Pixel", "Google")
        )
    }

    // for now, keep the selections in memory
    private val selectedTopics = MutableStateFlow(setOf<TopicSelection>())

    // Used to make suspend functions that read and update state safe to call from any thread
    private val mutex = Mutex()

    /**
     * Toggle between selected and unselected
     */
    suspend fun toggleTopicSelection(topic: TopicSelection) {
        mutex.withLock {
            val set = selectedTopics.value.toMutableSet()
            set.addOrRemove(topic)
            selectedTopics.value = set
        }
    }

    /**
     * Currently selected topics
     */
    fun observeTopicsSelected(): Flow<Set<TopicSelection>> = selectedTopics
}

data class TopicSelection(val section: String, val topic: String)
