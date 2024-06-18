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
package com.compose.performance.measure

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import androidx.tracing.Trace
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class ChangeTimeZoneBenchmark : AbstractBenchmark(StartupMode.WARM) {
    @Test
    fun changeTimeZoneCompilationFull() = benchmark(CompilationMode.Full())

    override val metrics: List<Metric> =
        listOf(
            FrameTimingMetric(),
            TraceSectionMetric("PublishDate.registerReceiver", TraceSectionMetric.Mode.Sum)
        )

    override fun MacrobenchmarkScope.setupBlock() {
        device.changeTimeZone("America/Los_Angeles")
        pressHome()
        startTaskActivity("accelerate_heavy")
        device.wait(Until.hasObject(By.res("list_of_items")), 5_000)
    }

    override fun MacrobenchmarkScope.measureBlock() {
        device.changeTimeZone("Europe/Warsaw")
        device.changeTimeZone("America/Los_Angeles")
    }
}

fun UiDevice.changeTimeZone(zoneId: String) {
    // We add here a trace section, so that we can easily find in the trace where the timezone change occurs
    Trace.beginAsyncSection("change timezone", 0)
    executeShellCommand("service call alarm 3 s16 $zoneId")
    Trace.endAsyncSection("change timezone", 0)
    Thread.sleep(1_000)
}
