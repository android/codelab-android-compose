package com.compose.compose_performance_workshop.phases

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
import androidx.compose.ui.unit.IntSize
import com.compose.compose_performance_workshop.R

internal const val MOVE_SPEED = 10

@Composable
fun PhasesDVDLogo() {
    val logo = painterResource(id = R.drawable.dvd_logo)
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
                modifier = Modifier.offset(logoPosition.first.toDp(), logoPosition.second.toDp())
            )
        }
    }
}

@Composable
fun logoPosition(size: IntSize, logoSize: Size): State<Pair<Int, Int>> {
    var x by remember { mutableStateOf(0) }
    var xDirection by remember { mutableStateOf(1) }
    var y by remember { mutableStateOf(0) }
    var yDirection by remember { mutableStateOf(1) }

    return produceState(initialValue = 0 to 0, size, logoSize) {
        if (size == IntSize.Zero) {
            this.value = 0 to 0
        } else {
            while (true) {
                withFrameMillis {
                    x += MOVE_SPEED * xDirection
                    if (x <= 0 || x >= size.width - logoSize.width) {
                        xDirection *= -1
                    }

                    y += MOVE_SPEED * yDirection
                    if (y <= 0 || y >= size.height - logoSize.height) {
                        yDirection *= -1
                    }

                    this.value = x to y
                }
            }
        }
    }
}
