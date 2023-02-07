/*
 * Copyright 2022 The Android Open Source Project
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.reply.ui.navigation.ReplyBottomNavigationBar
import com.example.reply.ui.navigation.ReplyNavigationActions
import com.example.reply.ui.navigation.ReplyRoute
import com.example.reply.ui.navigation.ReplyTopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyApp(
    replyHomeUIState: ReplyHomeUIState,
    closeDetailScreen: () -> Unit = {},
    navigateToDetail: (Long) -> Unit
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        ReplyNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: ReplyRoute.INBOX

    ReplyAppContent(
        replyHomeUIState = replyHomeUIState,
        navController = navController,
        selectedDestination = selectedDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo,
        closeDetailScreen = closeDetailScreen,
        navigateToDetail = navigateToDetail
    )
}

@Composable
fun ReplyAppContent(
    modifier: Modifier = Modifier,
    replyHomeUIState: ReplyHomeUIState,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (ReplyTopLevelDestination) -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ReplyNavHost(
            navController = navController,
            replyHomeUIState = replyHomeUIState,
            closeDetailScreen = closeDetailScreen,
            navigateToDetail = navigateToDetail,
            modifier = Modifier.weight(1f),
        )
        ReplyBottomNavigationBar(
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = navigateToTopLevelDestination
        )
    }
}

@Composable
private fun ReplyNavHost(
    navController: NavHostController,
    replyHomeUIState: ReplyHomeUIState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ReplyRoute.INBOX,
    ) {
        composable(ReplyRoute.INBOX) {
            ReplyInboxScreen(
                replyHomeUIState = replyHomeUIState,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
            )
        }
        composable(ReplyRoute.DM) {
            EmptyComingSoon()
        }
        composable(ReplyRoute.ARTICLES) {
            EmptyComingSoon()
        }
        composable(ReplyRoute.GROUPS) {
            EmptyComingSoon()
        }
    }
}
