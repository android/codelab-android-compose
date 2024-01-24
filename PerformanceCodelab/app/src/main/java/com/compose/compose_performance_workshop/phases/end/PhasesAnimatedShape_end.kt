package com.compose.compose_performance_workshop.phases.end

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.compose_performance_workshop.ui.theme.Purple40
import com.compose.compose_performance_workshop.ui.theme.Purple80

@Composable
fun PhasesAnimatedShape_end() {
    val color by rememberInfiniteTransition("box_transition")
        .animateColor(
            initialValue = Purple80,
            targetValue = Purple40,
            animationSpec = infiniteRepeatable(
                tween(333), RepeatMode.Reverse
            ), label = "box_color"
        )
    var size by remember { mutableStateOf(16) }
    val sizeAnimated by animateDpAsState(
        targetValue = size.dp,
        label = "box_size",
        animationSpec = spring(Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessVeryLow)
    )

    Box {
        AnimatedCircle(
            color = { color },
            size = { sizeAnimated },
            modifier = Modifier.align(Alignment.Center)
        )

        Slider(
            value = size.toFloat(),
            onValueChange = { size = it.toInt() },
            valueRange = 64f..512f,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
    }
}

@Composable
fun AnimatedCircle(color: ()-> Color, size: ()-> Dp, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        val colorVal = color()
        val sizeVal = size()

        drawCircle(color = colorVal, radius = sizeVal.toPx() / 2)
    })
}
