/*
 * Copyright 2020 The Android Open Source Project
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

package com.codelab.theming.ui.finish

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.codelab.theming.R
import com.codelab.theming.data.Post
import com.codelab.theming.data.PostRepo
import com.codelab.theming.ui.finish.theme.JetnewsTheme

@Composable
fun Home() {
    val featured = remember { PostRepo.getFeaturedPost() }
    val posts = remember { PostRepo.getPosts() }
    JetnewsTheme {
        Scaffold(
            topBar = { AppBar() }
        ) { innerPadding ->
            ScrollableColumn(contentPadding = innerPadding) {
                Header(stringResource(R.string.top))
                FeaturedPost(
                    post = featured,
                    modifier = Modifier.padding(16.dp)
                )
                Header(stringResource(R.string.popular))
                posts.forEach { post ->
                    PostItem(post = post)
                    Divider(startIndent = 72.dp)
                }
            }
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(Icons.Rounded.Palette, Modifier.padding(horizontal = 12.dp))
        },
        title = {
            Text(text = stringResource(R.string.app_title))
        },
        backgroundColor = MaterialTheme.colors.primarySurface
    )
}

@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun FeaturedPost(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* onClick */ }
        ) {
            Image(
                asset = imageResource(post.imageId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredHeightIn(min = 180.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.preferredHeight(16.dp))

            val padding = Modifier.padding(horizontal = 16.dp)
            ProvideEmphasis(EmphasisAmbient.current.high) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h6,
                    modifier = padding
                )
                Text(
                    text = post.metadata.author.name,
                    style = MaterialTheme.typography.body2,
                    modifier = padding
                )
            }
            PostMetadata(post, padding)
            Spacer(Modifier.preferredHeight(16.dp))
        }
    }
}

@Composable
private fun PostMetadata(
    post: Post,
    modifier: Modifier = Modifier
) {
    val divider = "  •  "
    val tagDivider = "  "
    val text = annotatedString {
        append(post.metadata.date)
        append(divider)
        append(stringResource(R.string.read_time, post.metadata.readTimeMinutes))
        append(divider)
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )
        post.tags.forEachIndexed { index, tag ->
            if (index != 0) {
                append(tagDivider)
            }
            withStyle(tagStyle) {
                append(" ${tag.toUpperCase()} ")
            }
        }
    }
    ProvideEmphasis(EmphasisAmbient.current.medium) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = modifier
        )
    }
}

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable { /* todo */ }
            .padding(vertical = 8.dp),
        icon = {
            Image(
                asset = imageResource(post.imageThumbId),
                modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
            )
        },
        text = {
            Text(text = post.title)
        },
        secondaryText = {
            PostMetadata(post)
        }
    )
}

@Preview("Post Item")
@Composable
private fun PostItemPreview() {
    val post = remember { PostRepo.getFeaturedPost() }
    JetnewsTheme {
        Surface {
            PostItem(post = post)
        }
    }
}

@Preview("Featured Post")
@Composable
private fun FeaturedPostPreview() {
    val post = remember { PostRepo.getFeaturedPost() }
    JetnewsTheme {
        FeaturedPost(post = post)
    }
}

@Preview("Featured Post • Dark")
@Composable
private fun FeaturedPostDarkPreview() {
    val post = remember { PostRepo.getFeaturedPost() }
    JetnewsTheme(darkTheme = true) {
        FeaturedPost(post = post)
    }
}

@Preview("Home")
@Composable
private fun HomePreview() {
    Home()
}
