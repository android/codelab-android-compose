package com.compose.compose_performance_workshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import com.compose.compose_performance_workshop.derivedstate.Screen1
import com.compose.compose_performance_workshop.derivedstate.Screen2
import com.compose.compose_performance_workshop.phases.PhasesAnimatedShape
import com.compose.compose_performance_workshop.phases.PhasesDVDLogo
import com.compose.compose_performance_workshop.stability.StabilityScreen1
import com.compose.compose_performance_workshop.ui.theme.PerformanceWorkshopTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerformanceWorkshopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        // TODO task #1: make composables accessible from UiAutomator
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedItem by remember { mutableStateOf<Sample?>(null) }
                    var samples by remember { mutableStateOf<List<Sample>?>(null) }

                    LaunchedEffect(Unit) {
                        // Faking asynchronous loading
                        delay(1000)
                        samples = Sample.entries
                    }

                    BackHandler {
                        selectedItem = null
                    }

                    when {
                        selectedItem != null -> selectedItem?.composable?.invoke()

                        samples != null -> {
                            // TODO task #1: wait for TTFD
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                                // TODO task #1: make composables accessible from UiAutomator
                            ) {
                                items(samples!!) { sample ->
                                    ListItem(
                                        headlineContent = {
                                            Text(sample.label)
                                        },
                                        modifier = Modifier
                                            .clickable { selectedItem = sample }
                                        // TODO task #1: make composables accessible from UiAutomator
                                    )
                                }
                            }
                        }

                        else -> {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}


private enum class Sample(
    val label: String,
    val testTag: String,
    val composable: @Composable () -> Unit,
) {
    PhasesAnimatedShape(
        label = "Phases - Animating Shape",
        testTag = "phases_animating_shape",
        composable = { PhasesAnimatedShape() }
    ),
    PhasesDVD(
        label = "Phases - DVD Logo",
        testTag = "phases_dvd",
        composable = { PhasesDVDLogo() }
    ),
    StabilityList(
        label = "Stability - Stable LazyList",
        testTag = "stability_list",
        composable = { StabilityScreen1() }
    ),
    DerivedState1(
        label = "Derived State - Scrolling List",
        testTag = "derived_scroll",
        composable = { Screen1() }
    ),
    DerivedState2(
        label = "Derived State - Form",
        testTag = "derived_form",
        composable = { Screen2() }
    )
}
