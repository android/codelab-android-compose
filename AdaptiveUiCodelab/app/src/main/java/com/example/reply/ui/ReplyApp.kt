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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowWidthSizeClass
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
