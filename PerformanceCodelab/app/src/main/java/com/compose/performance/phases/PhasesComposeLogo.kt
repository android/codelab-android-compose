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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.trace
import com.compose.performance.R


@Composable
fun PhasesComposeLogo() = trace("PhasesComposeLogo") {
    val logo = painterResource(id = R.drawable.compose_logo)
    var size by remember { mutableStateOf(IntSize.Zero) }
    val logoPosition by logoPosition(size = size, logoSize = logo.intrinsicSize)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onPlaced {
                size = it.size
            }
    ) {
        with(LocalDensity.current) {
            Image(
                painter = logo,
                contentDescription = "logo",
                modifier = Modifier.offset(logoPosition.x.toDp(), logoPosition.y.toDp())
            )
        }
    }
}

@Composable
fun logoPosition(size: IntSize, logoSize: Size): State<IntOffset> =
    produceState(initialValue = IntOffset.Zero, size, logoSize) {
        if (size == IntSize.Zero) {
            this.value = IntOffset.Zero
            return@produceState
        }

        var xDirection = 1
        var yDirection = 1

        while (true) {
            withFrameMillis {
                value += IntOffset(x = MOVE_SPEED * xDirection, y = MOVE_SPEED * yDirection)

                if (value.x <= 0 || value.x >= size.width - logoSize.width) {
                    xDirection *= -1
                }

                if (value.y <= 0 || value.y >= size.height - logoSize.height) {
                    yDirection *= -1
                }
            }
        }
    }

internal const val MOVE_SPEED = 10
