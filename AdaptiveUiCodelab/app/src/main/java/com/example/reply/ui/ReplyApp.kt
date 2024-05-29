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

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.reply.data.Email
import com.example.reply.ui.utils.DevicePosture

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture,
    replyHomeUIState: ReplyHomeUIState,
    onEmailClick: (Email) -> Unit,
) {
    ReplyNavigationWrapperUI {
        ReplyAppContent(
            replyHomeUIState = replyHomeUIState,
            onEmailClick = onEmailClick
        )
    }
}

@Composable
private fun ReplyNavigationWrapperUI(
    content: @Composable () -> Unit = {}
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
        content()
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ReplyAppContent(
    replyHomeUIState: ReplyHomeUIState,
    onEmailClick: (Email) -> Unit,
) {
    val selectedEmail = replyHomeUIState.selectedEmail
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
           AnimatedPane {
               ReplyListPane(
                   replyHomeUIState = replyHomeUIState,
                   onEmailClick = { email ->
                       onEmailClick(email)
                       navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, email.id)
                   }
               )
           }
        },
        detailPane = {
            AnimatedPane {
                if (selectedEmail != null) {
                    ReplyDetailPane(selectedEmail)
                }
            }
        }
    )
}
