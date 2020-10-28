/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.ui.graphics.vector.VectorAsset
import java.util.UUID

data class TodoItem(
    val task: String,
    val icon: TodoIcon = TodoIcon.Default,
    // since the user may generate identical tasks, give them each a unique ID
    val id: UUID = UUID.randomUUID()
)

enum class TodoIcon(val vectorAsset: VectorAsset) {
    Square(Icons.Default.CropSquare),
    Done(Icons.Default.Done),
    Event(Icons.Default.Event),
    Privacy(Icons.Default.PrivacyTip),
    Trash(Icons.Default.RestoreFromTrash);

    companion object {
        val Default = Square
    }
}
