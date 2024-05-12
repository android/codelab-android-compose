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

import android.graphics.Point
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
import androidx.test.uiautomator.Until
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class AccelerateHeavyScreenBenchmark : AbstractBenchmark(StartupMode.COLD) {

    @Test
    fun accelerateHeavyScreenCompilationFull() = benchmark(CompilationMode.Full())

    override val metrics: List<Metric> =
        listOf(
            FrameTimingMetric(),
            TraceSectionMetric("ImagePlaceholder", TraceSectionMetric.Mode.Sum),
            TraceSectionMetric("PublishDate.registerReceiver", TraceSectionMetric.Mode.Sum),
            TraceSectionMetric("ItemTag", TraceSectionMetric.Mode.Sum)
        )

    override fun MacrobenchmarkScope.measureBlock() {
        pressHome()
        startTaskActivity("accelerate_heavy")

        device.wait(Until.hasObject(By.res("list_of_items")), 5_000)
        val feed = device.findObject(By.res("list_of_items"))
        feed.setGestureMargin(device.displayWidth / 5)

        repeat(2) {
            feed.drag(Point(feed.visibleCenter.x, feed.visibleBounds.top))
            Thread.sleep(500)
        }
    }
}
