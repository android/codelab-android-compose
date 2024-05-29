/*
 * Copyright 2022 Google LLC
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

package com.example.reply.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.reply.R

/** Navigation destinations in the app. */
enum class ReplyDestination(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
) {
    Inbox(R.string.tab_inbox, Icons.Default.Inbox),

    Articles(R.string.tab_article, Icons.Default.Article),

    Messages(R.string.tab_dm, Icons.Outlined.Chat),

    Groups(R.string.tab_groups, Icons.Outlined.People),
}
