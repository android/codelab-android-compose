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
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import org.junit.Rule

abstract class AbstractBenchmark(
    protected val startupMode: StartupMode = StartupMode.WARM,
    // For the purpose of this workshop, we have iterations set to low number.
    // For more accurate measurements, you should increase the iterations to at least 10
    // based on how much variance occurs in the results. In general more iterations = more stable
    // results, but longer execution time.
    protected val iterations: Int = 1
) {
    @get:Rule
    val rule = MacrobenchmarkRule()

    abstract val metrics: List<Metric>

    open fun MacrobenchmarkScope.setupBlock() {}
    abstract fun MacrobenchmarkScope.measureBlock()

    fun benchmark(compilationMode: CompilationMode) {
        rule.measureRepeated(
            packageName = "com.compose.performance",
            metrics = metrics,
            compilationMode = compilationMode,
            startupMode = startupMode,
            iterations = iterations,
            setupBlock = { setupBlock() },
            measureBlock = { measureBlock() }
        )
    }
}

fun MacrobenchmarkScope.startTaskActivity(task: String) =
    startActivityAndWait { it.putExtra("EXTRA_START_TASK", task) }
