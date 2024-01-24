package com.compose.compose_performance_workshop.measure

import android.graphics.Point
import android.widget.SeekBar
import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FrameTimingBenchmark {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() =
        benchmark(CompilationMode.None())

    @Test
    fun startupCompilationBaselineProfiles() =
        benchmark(CompilationMode.Partial(BaselineProfileMode.Require))

    @Test
    fun startupCompilationFull() =
        benchmark(CompilationMode.Full())

    private fun benchmark(compilationMode: CompilationMode) {
        rule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = listOf(
                FrameTimingMetric(),
            ),
            compilationMode = compilationMode,
            startupMode = StartupMode.WARM,
            iterations = 10,
            setupBlock = {
                pressHome()
                startActivityAndWait()
                device.wait(Until.hasObject(By.res("samples_list")), 5_000)

                // Select stability item from the samples list
                val sampleItem = device.findObject(By.res("phases_animating_shape"))
                sampleItem.click()

                // Wait until FAB is shown on screen
                device.wait(Until.hasObject(By.clazz(SeekBar::class.java)), 5_000)
            },
            measureBlock = {
                // Actual code to measure
                val seekBar = device.findObject(By.clazz(SeekBar::class.java))

                seekBar.drag(Point(seekBar.visibleBounds.right, 0))
                Thread.sleep(500)
            }
        )
    }
}
