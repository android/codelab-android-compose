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

package com.codelabs.state.examples

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.R


// An example of a stateful and stateless composable using unidirectional data flow. In this codelab
// you will learn how to build composables following these patterns and how it helps you structure
// your code.

@Composable
fun ExpandingCard(title: String, body: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    ExpandingCard(
        title = title,
        body = body,
        expanded = expanded,
        onExpand = { expanded = true },
        onCollapse = { expanded = false },
        modifier = modifier
    )
}

@Composable
fun ExpandingCard(
    title: String,
    body: String,
    expanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column(
            Modifier
                .width(280.dp)
                .animateContentSize() // automatically animate size when it changes
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(title)
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(body)
                IconButton(onClick = onCollapse, Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.ExpandLess,
                        contentDescription = stringResource(id = R.string.cd_collapse)
                    )
                }
            } else {
                IconButton(onClick = onExpand, Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = stringResource(id = R.string.cd_expand)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExpandingCard() {
    Box(Modifier.fillMaxSize()) {
        ExpandingCard(
            title = "Title text",
            body =
            """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eleifend, augue quis fermentum feugiat, neque lacus elementum velit, ut molestie quam ligula at magna. Etiam dictum in nulla a posuere. Integer nisl tortor, mollis id hendrerit quis, tincidunt eget dolor. Nulla tempor leo tellus, ac aliquam nunc ornare sed. Aliquam ut odio rutrum, convallis mi vel, fringilla nibh. Vivamus vel mi rutrum, vehicula metus nec, efficitur risus. Phasellus vel blandit libero. Proin leo mauris, iaculis a eleifend vitae, malesuada a dolor. Suspendisse euismod bibendum sapien tincidunt dapibus. Quisque elit dui, dictum in sem eget, ultricies condimentum ante. Praesent elementum tincidunt mi, at vulputate turpis volutpat non.
""",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
