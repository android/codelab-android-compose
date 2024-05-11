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
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@OptIn(ExperimentalMetricApi::class)
class StabilityBenchmark : AbstractBenchmark(StartupMode.WARM) {

    @Test
    fun stabilityCompilationFull() = benchmark(CompilationMode.Full())

    override val metrics: List<Metric> = listOf(
        FrameTimingMetric(),
        TraceSectionMetric("latest_change", TraceSectionMetric.Mode.Sum),
        TraceSectionMetric("item_row", TraceSectionMetric.Mode.Sum)
    )

    override fun MacrobenchmarkScope.setupBlock() {
        pressHome()
        startTaskActivity("stability_screen")
    }

    override fun MacrobenchmarkScope.measureBlock() {
        // Actual code to measure
        val fab = device.findObject(By.res("fab"))

        repeat(3) {
            fab.click()
            Thread.sleep(300)
        }
    }
}
