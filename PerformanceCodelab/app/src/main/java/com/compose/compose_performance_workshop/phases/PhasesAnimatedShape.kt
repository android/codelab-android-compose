package com.compose.compose_performance_workshop.phases

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.compose_performance_workshop.ui.theme.Purple80

@Composable
fun PhasesAnimatedShape() {
    val color = Purple80

    /* EXTENSION */
//    val color by rememberInfiniteTransition("box_transition")
//        .animateColor(
//            initialValue = Purple80,
//            targetValue = Purple40,
//            animationSpec = infiniteRepeatable(
//                tween(333), RepeatMode.Reverse
//            ), label = "box_color"
//        )

    var size by remember { mutableStateOf(64f) }
    val sizeAnimated by animateDpAsState(
        targetValue = size.dp,
        label = "box_size",
        animationSpec = spring(Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessVeryLow)
    )

    Box {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = color, shape = CircleShape)
                .size(sizeAnimated)
        )

        Slider(
            value = size,
            onValueChange = { size = it },
            valueRange = 64f..256f,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
    }
}