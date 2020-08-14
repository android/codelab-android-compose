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

package com.codelabs.state.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animate
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSizeConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

/**
 * Draws a row of [TodoIcon] with visibility changes animated.
 *
 * When not visible, will collapse to 16.dp high by default. You can enlarge this with the passed
 * modifier.
 *
 * @param icon (state) the current selected icon
 * @param onIconChange (event) request the selected icon change
 * @param modifier modifier for this element
 * @param visible (state) if the icon should be shown
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    initialVisibility: Boolean = false
) {
    Box(modifier.defaultMinSizeConstraints(minHeight = 16.dp)) {
        AnimatedVisibility(
            visible = visible,
            initiallyVisible = initialVisibility,
            enter = fadeIn(animSpec = TweenSpec(300, easing = FastOutLinearInEasing)),
            exit = fadeOut(animSpec = TweenSpec(100, easing = FastOutSlowInEasing)),
        ) {
            IconRow(icon, onIconChange)
        }
    }
}

/**
 * Displays a row of selectable [TodoIcon]
 *
 * @param icon (state) the current selected icon
 * @param onIconChange (event) request the selected icon change
 * @param modifier modifier for this element
 */
@Composable
fun IconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        for (todoIcon in TodoIcon.values()) {
            SelectableIconButton(
                icon = todoIcon.vectorAsset,
                onIconSelected = { onIconChange(todoIcon) },
                isSelected = todoIcon == icon
            )
        }
    }
}

/**
 * Displays a single icon that can be selected.
 *
 * @param icon the icon to draw
 * @param onIconSelected (event) request this icon be selected
 * @param isSelected (state) selection state
 * @param modifier modifier for this element
 */
@OptIn(ExperimentalLayout::class)
@Composable
private fun SelectableIconButton(
    icon: VectorAsset,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }
    Button(
        onClick = { onIconSelected() },
        shape = CircleShape,
        backgroundColor = Color.Transparent,
        border = null,
        elevation = 0.dp,
        modifier = modifier
    ) {
        Column {
            Icon(icon, tint = tint)

            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .preferredWidth(icon.defaultWidth)
                        .preferredHeight(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.preferredHeight(4.dp))
            }
        }
    }
}

/**
 * Draw a background based on [MaterialTheme.colors.onSurface] that animates resizing and elevation
 * changes.
 *
 * @param elevate draw a shadow, changes to this will be animated
 * @param modifier modifier for this element
 * @param content (slot) content to draw in the background
 */
@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val animatedElevation = animate(if (elevate) 1.dp else 0.dp, TweenSpec(500))
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        elevation = animatedElevation,
        shape = RectangleShape,
    ) {
        Row(
            modifier = modifier.animateContentSize(animSpec = TweenSpec(300)),
            children = content
        )
    }
}

/**
 * Styled [TextField] for inputting a [TodoItem]
 *
 * @param text (state) current text to display
 * @param onTextChange (event) request the text change state
 * @param modifier the modifier for this element
 * @param onImeAction (event) notify caller of [ImeAction.Done] events
 */
@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) = TextField(
    value = text,
    onValueChange = { onTextChange(it) },
    label = { /* no label */ },
    backgroundColor = Color.Transparent,
    imeAction = ImeAction.Done,
    onImeActionPerformed = { action, softKeyboardController ->
        if (action == ImeAction.Done) {
            onImeAction()
            softKeyboardController?.hideSoftwareKeyboard()
        }
    },
    modifier = modifier
)

/**
 * Styled button for [TodoScreen]
 *
 * @param onClick (event) notify caller of click events
 * @param text button text
 * @param modifier modifier for button
 * @param enabled enable or disable the button
 */
@Composable
fun TodoEditButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        elevation = 0.dp,
        modifier = modifier
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun PreviewIconRow() = IconRow(icon = TodoIcon.Square, onIconChange = {})
