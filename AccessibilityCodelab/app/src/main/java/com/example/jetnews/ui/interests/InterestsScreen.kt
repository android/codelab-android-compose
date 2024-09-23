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

package com.example.jetnews.ui.interests

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnews.R
import com.example.jetnews.data.interests.InterestsRepository
import com.example.jetnews.data.interests.TopicSelection
import com.example.jetnews.data.interests.TopicsMap
import com.example.jetnews.ui.components.InsetAwareTopAppBar
import com.example.jetnews.ui.theme.JetnewsTheme
import kotlinx.coroutines.launch

/**
 * Stateful InterestsScreen that handles the interaction with the repository
 *
 * @param interestsRepository data source for this screen
 * @param openDrawer (event) request opening the app drawer
 */
@Composable
fun InterestsScreen(
    interestsRepository: InterestsRepository,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Returns a [CoroutineScope] that is scoped to the lifecycle of [InterestsScreen]. When this
    // screen is removed from composition, the scope will be cancelled.
    val coroutineScope = rememberCoroutineScope()

    // collectAsState will read a [Flow] in Compose
    val selectedTopics by interestsRepository.observeTopicsSelected().collectAsState(setOf())
    val onTopicSelect: (TopicSelection) -> Unit = {
        coroutineScope.launch { interestsRepository.toggleTopicSelection(it) }
    }
    InterestsScreen(
        topics = interestsRepository.topics,
        selectedTopics = selectedTopics,
        onTopicSelect = onTopicSelect,
        openDrawer = openDrawer,
        modifier = modifier,
    )
}

/**
 * Stateless interest screen displays the topics the user can subscribe to
 *
 * @param topics (state) topics to display, mapped by section
 * @param selectedTopics (state) currently selected topics
 * @param onTopicSelect (event) request a topic selection be changed
 * @param openDrawer (event) request opening the app drawer
 */
@Composable
fun InterestsScreen(
    topics: TopicsMap,
    selectedTopics: Set<TopicSelection>,
    onTopicSelect: (TopicSelection) -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            InsetAwareTopAppBar(
                title = { Text("Interests") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(
                            painter = painterResource(R.drawable.ic_jetnews_logo),
                            contentDescription = stringResource(R.string.cd_open_navigation_drawer)
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding)
        ) {
            topics.forEach { (section, topics) ->
                item {
                    Text(
                        text = section,
                        modifier = Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                items(topics) { topic ->
                    TopicItem(
                        itemTitle = topic,
                        selected = selectedTopics.contains(TopicSelection(section, topic))
                    ) { onTopicSelect(TopicSelection(section, topic)) }
                    TopicDivider()
                }
            }
        }
    }
}

/**
 * Display a full-width topic item
 *
 * @param itemTitle (state) topic title
 * @param selected (state) is topic currently selected
 * @param onToggle (event) toggle selection for topic
 */
@Composable
private fun TopicItem(itemTitle: String, selected: Boolean, onToggle: () -> Unit) {
    val image = painterResource(R.drawable.placeholder_1_1)
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(56.dp, 56.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = itemTitle,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))
        Checkbox(
            checked = selected,
            onCheckedChange = { onToggle() },
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

/**
 * Full-width divider for topics
 */
@Composable
private fun TopicDivider() {
    Divider(
        modifier = Modifier.padding(start = 90.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    )
}

@Preview("Interests screen", "Interests")
@Preview("Interests screen (dark)", "Interests", uiMode = UI_MODE_NIGHT_YES)
@Preview("Interests screen (big font)", "Interests", fontScale = 1.5f)
@Preview("Interests screen (large screen)", "Interests", device = Devices.PIXEL_C)
@Composable
fun PreviewInterestsScreen() {
    JetnewsTheme {
        InterestsScreen(
            interestsRepository = InterestsRepository(),
            openDrawer = {}
        )
    }
}
