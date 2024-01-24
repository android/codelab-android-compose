package com.compose.compose_performance_workshop.phases.end

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.compose.compose_performance_workshop.R
import com.compose.compose_performance_workshop.phases.MOVE_SPEED

@Composable
fun PhasesDVDLogo_end() {
    val logo = painterResource(id = R.drawable.dvd_logo)
    var size by remember { mutableStateOf(IntSize.Zero) }
    val logoPosition by logoPosition_end(size = size, logoSize = logo.intrinsicSize)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onPlaced {
                size = it.size
            }
    ) {
        Image(
            painter = logo,
            contentDescription = "logo",
            modifier = Modifier.offset { logoPosition }
        )
    }
}


@Composable
fun logoPosition_end(size: IntSize, logoSize: Size): State<IntOffset> {
    var x by remember { mutableStateOf(0) }
    var xDirection by remember { mutableStateOf(1) }
    var y by remember { mutableStateOf(0) }
    var yDirection by remember { mutableStateOf(1) }

    return produceState(initialValue = IntOffset.Zero, size, logoSize) {
        if (size == IntSize.Zero) {
            this.value = IntOffset.Zero
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

                    this.value = IntOffset(x, y)
                }
            }
        }
    }
}
