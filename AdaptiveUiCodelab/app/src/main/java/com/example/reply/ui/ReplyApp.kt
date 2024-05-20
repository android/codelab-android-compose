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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.reply.R
import com.example.reply.ui.utils.DevicePosture
import com.example.reply.ui.utils.ReplyContentType

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    replyHomeUIState: ReplyHomeUIState
) {
    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     *
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val contentType: ReplyContentType

    when (windowSize) {
        WindowWidthSizeClass.COMPACT -> {
            contentType = ReplyContentType.LIST_ONLY
        }
        WindowWidthSizeClass.MEDIUM -> {
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ReplyContentType.LIST_AND_DETAIL
            } else {
                ReplyContentType.LIST_ONLY
            }
        }
        WindowWidthSizeClass.EXPANDED -> {
            contentType = ReplyContentType.LIST_AND_DETAIL
        }
        else -> {
            contentType = ReplyContentType.LIST_ONLY
        }
    }

    ReplyNavigationWrapperUI(contentType, replyHomeUIState)
}

@Composable
private fun ReplyNavigationWrapperUI(
    contentType: ReplyContentType,
    replyHomeUIState: ReplyHomeUIState
) {
    var selectedDestination: ReplyDestination by remember {
        mutableStateOf(ReplyDestination.Inbox)
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            ReplyDestination.entries.forEach {
                item(
                    label = { Text(stringResource(it.labelRes)) },
                    icon = { Icon(it.icon, stringResource(it.labelRes)) },
                    selected = it == selectedDestination,
                    onClick = { /*TODO update selection*/ },
                )
            }
        }
    ) {
        ReplyAppContent(contentType, replyHomeUIState)
    }
}


@Composable
fun ReplyAppContent(
    contentType: ReplyContentType,
    replyHomeUIState: ReplyHomeUIState,
) {
    if (contentType == ReplyContentType.LIST_AND_DETAIL) {
        ReplyListAndDetailContent(
            replyHomeUIState = replyHomeUIState,
            modifier = Modifier.fillMaxSize(),
        )
    } else {
        ReplyListOnlyContent(
            replyHomeUIState = replyHomeUIState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun NavigationDrawerContent(
    selectedDestination: ReplyDestination,
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {}
) {
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(24.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = onDrawerClicked) {
                Icon(
                    imageVector = Icons.Default.MenuOpen,
                    contentDescription = stringResource(id = R.string.navigation_drawer)
                )
            }
        }

        ReplyDestination.entries.forEach {
            NavigationDrawerItem(
                selected = selectedDestination == it,
                label = {
                    Text(
                        text = stringResource(it.labelRes),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = { Icon(it.icon, stringResource(it.labelRes)) },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { /*TODO*/ },
            )
        }
    }
}
