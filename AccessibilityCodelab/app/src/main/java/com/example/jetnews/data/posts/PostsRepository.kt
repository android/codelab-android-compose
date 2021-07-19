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

package com.example.jetnews.data.posts

import com.example.jetnews.data.posts.impl.posts
import com.example.jetnews.model.Post
import com.example.jetnews.utils.addOrRemove
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Simplified implementation of PostsRepository that returns a hardcoded list of
 * posts with resources synchronously.
 */
class PostsRepository {
    // for now, keep the favorites in memory
    private val favorites = MutableStateFlow<Set<String>>(setOf())

    /**
     * Get a specific JetNews post.
     */
    fun getPost(postId: String?): Post? {
        return posts.find { it.id == postId }
    }

    /**
     * Get JetNews posts.
     */
    fun getPosts(): List<Post> {
        return posts
    }

    /**
     * Observe the current favorites
     */
    fun observeFavorites(): Flow<Set<String>> = favorites

    /**
     * Toggle a postId to be a favorite or not.
     */
    fun toggleFavorite(postId: String) {
        val set = favorites.value.toMutableSet()
        set.addOrRemove(postId)
        favorites.value = set
    }
}
