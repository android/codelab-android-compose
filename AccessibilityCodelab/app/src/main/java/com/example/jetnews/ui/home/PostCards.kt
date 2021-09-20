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

package com.example.jetnews.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnews.R
import com.example.jetnews.data.posts.impl.post1
import com.example.jetnews.data.posts.impl.post3
import com.example.jetnews.model.Post
import com.example.jetnews.ui.theme.JetnewsTheme

@Composable
fun PostCardHistory(post: Post, navigateToArticle: (String) -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    Row(
        Modifier.clickable { navigateToArticle(post.id) }
    ) {
        Image(
            painter = painterResource(post.imageThumbId),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .size(40.dp, 40.dp)
                .clip(MaterialTheme.shapes.small)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(post.title, style = MaterialTheme.typography.subtitle1)
            Row(Modifier.padding(top = 4.dp)) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    val textStyle = MaterialTheme.typography.body2
                    Text(
                        text = post.metadata.author.name,
                        style = textStyle
                    )
                    Text(
                        text = " - ${post.metadata.readTimeMinutes} min read",
                        style = textStyle
                    )
                }
            }
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.cd_show_fewer),
                modifier = Modifier
                    .clickable { openDialog = true }
                    .size(24.dp)
            )
        }
    }
    if (openDialog) {
        AlertDialog(
            modifier = Modifier.padding(20.dp),
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = stringResource(id = R.string.fewer_stories),
                    style = MaterialTheme.typography.h6
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.fewer_stories_content),
                    style = MaterialTheme.typography.body1
                )
            },
            confirmButton = {
                Text(
                    text = stringResource(id = R.string.agree),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable { openDialog = false }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostCardPopular(
    post: Post,
    navigateToArticle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.size(280.dp, 240.dp),
        onClick = { navigateToArticle(post.id) }
    ) {
        Column {

            Image(
                painter = painterResource(post.imageId),
                contentDescription = null, // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = post.metadata.author.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    text = stringResource(
                        id = R.string.home_post_min_read,
                        formatArgs = arrayOf(
                            post.metadata.date,
                            post.metadata.readTimeMinutes
                        )
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview("Regular colors")
@Preview("Dark colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPostCardPopular() {
    JetnewsTheme {
        Surface {
            PostCardPopular(post1, {})
        }
    }
}

@Preview("Post History card")
@Composable
fun HistoryPostPreview() {
    JetnewsTheme {
        Surface {
            PostCardHistory(post3, {})
        }
    }
}
