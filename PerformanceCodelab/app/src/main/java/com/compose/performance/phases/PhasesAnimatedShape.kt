/*
 * Copyright 2024 The Android Open Source Project
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
package com.compose.performance.phases

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.trace
import com.compose.performance.ui.theme.Purple80

private val smallSize = 64.dp
private val bigSize = 200.dp

@Composable
fun PhasesAnimatedShape() = trace("PhasesAnimatedShape") {
    var targetSize by remember { mutableStateOf(smallSize) }
    val size by animateDpAsState(
        targetValue = targetSize,
        label = "box_size",
        animationSpec = spring(Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessVeryLow)
    )

    Box(Modifier.fillMaxSize()) {
        MyShape(
            size = size,
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = {
                targetSize = if (targetSize == smallSize) {
                    bigSize
                } else {
                    smallSize
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text("Toggle Size")
        }
    }
}

@Composable
fun MyShape(size: Dp, modifier: Modifier = Modifier) = trace("MyShape") {
    Box(
        modifier = modifier
            .background(color = Purple80, shape = CircleShape)
            .size(size)
    )
}
