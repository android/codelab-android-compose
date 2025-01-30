package com.example.compose.rally.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.IdlingPolicies
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AnimatedCircleBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun testAnimatedCircleRendersCorrectly() {
        val proportions = listOf(0.3f, 0.3f, 0.4f)
        val colors = listOf(Color.Red, Color.Green, Color.Blue)

        composeTestRule.setContent {
            AnimatedCircle(proportions = proportions, colors = colors)
        }

        try {
            composeTestRule.onRoot().assertExists()
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }

    @Test(timeout = 15000)
    fun testAnimatedCircleWithEmptyProportions() {
        val proportions = emptyList<Float>()
        val colors = emptyList<Color>()

        composeTestRule.setContent {
            AnimatedCircle(proportions = proportions, colors = colors)
        }

        try {
            composeTestRule.onRoot().assertExists()
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }

    @Test(timeout = 15000)
    fun testAnimatedCircleWithSingleProportion() {
        val proportions = listOf(1.0f)
        val colors = listOf(Color.Red)

        composeTestRule.setContent {
            AnimatedCircle(proportions = proportions, colors = colors)
        }

        try {
            composeTestRule.onRoot().assertExists()
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }

    @Test(timeout = 15000)
    fun testAnimatedCircleWithMultipleProportions() {
        val proportions = listOf(0.2f, 0.3f, 0.1f, 0.4f)
        val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)

        composeTestRule.setContent {
            AnimatedCircle(proportions = proportions, colors = colors)
        }

        try {
            composeTestRule.onRoot().assertExists()
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }
}