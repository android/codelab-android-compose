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

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.reply.R
import com.example.reply.data.Email
import com.example.reply.ui.components.EmailDetailAppBar
import com.example.reply.ui.components.ReplyEmailListItem
import com.example.reply.ui.components.ReplyEmailThreadItem
import com.example.reply.ui.components.ReplySearchBar

@Composable
fun ReplyInboxScreen(
    replyHomeUIState: ReplyHomeUIState,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    val emailLazyListState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        ReplyEmailListContent(
            replyHomeUIState = replyHomeUIState,
            emailLazyListState = emailLazyListState,
            modifier = Modifier.fillMaxSize(),
            closeDetailScreen = closeDetailScreen,
            navigateToDetail = navigateToDetail
        )

        LargeFloatingActionButton(
            onClick = { /*Click Implementation*/ },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(id = R.string.edit),
                modifier = Modifier.size(28.dp)
            )
        }

    }
}

@Composable
fun ReplyEmailListContent(
    replyHomeUIState: ReplyHomeUIState,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long) -> Unit
) {
    if (replyHomeUIState.selectedEmail != null && replyHomeUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        ReplyEmailDetail(email = replyHomeUIState.selectedEmail) {
            closeDetailScreen()
        }
    } else {
        ReplyEmailList(
            emails = replyHomeUIState.emails,
            emailLazyListState = emailLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun ReplyEmailList(
    emails: List<Email>,
    emailLazyListState: LazyListState,
    selectedEmail: Email? = null,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyColumn(modifier = modifier, state = emailLazyListState) {
        item {
            ReplySearchBar(modifier = Modifier.fillMaxWidth())
        }
        items(items = emails, key = { it.id }) { email ->
            ReplyEmailListItem(
                email = email,
                isSelected = email.id == selectedEmail?.id
            ) { emailId ->
                navigateToDetail(emailId)
            }
        }
    }
}

@Composable
fun ReplyEmailDetail(
    email: Email,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    onBackPressed: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        item {
            EmailDetailAppBar(email, isFullScreen) {
                onBackPressed()
            }
        }
        items(items = email.threads, key = { it.id }) { email ->
            ReplyEmailThreadItem(email = email)
        }
    }
}
