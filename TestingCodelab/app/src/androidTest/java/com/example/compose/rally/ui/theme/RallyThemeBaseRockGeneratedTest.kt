package com.example.compose.rally.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.test.espresso.IdlingPolicies
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.graphics.compositeOver

@RunWith(AndroidJUnit4::class)
class RallyThemeBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun testRallyThemeAppliesCorrectColors() {
        lateinit var colors: androidx.compose.material.Colors

        composeTestRule.setContent {
            RallyTheme {
                colors = MaterialTheme.colors
            }
        }

        try {
            assert(colors.primary == Green500)
            assert(colors.surface == DarkBlue900)
            assert(colors.onSurface == Color.White)
            assert(colors.background == DarkBlue900)
            assert(colors.onBackground == Color.White)
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }

    @Test(timeout = 15000)
    fun testRallyThemeAppliesCorrectTypography() {
        lateinit var typography: androidx.compose.material.Typography

        composeTestRule.setContent {
            RallyTheme {
                typography = MaterialTheme.typography
            }
        }

        try {
            assert(typography.h1.fontWeight == FontWeight.W100)
            assert(typography.h1.fontSize == 96.sp)
            assert(typography.h2.fontWeight == FontWeight.SemiBold)
            assert(typography.h2.fontSize == 44.sp)
            assert(typography.h2.letterSpacing == 1.5.sp)
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }

    @Test(timeout = 15000)
    fun testRallyDialogThemeOverlayAppliesCorrectColors() {
        lateinit var colors: androidx.compose.material.Colors

        composeTestRule.setContent {
            RallyDialogThemeOverlay {
                colors = MaterialTheme.colors
            }
        }

        try {
            assert(colors.primary == Color.White)
            assert(colors.surface == Color.White.copy(alpha = 0.12f).compositeOver(Color.Black))
            assert(colors.onSurface == Color.White)
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }

    @Test(timeout = 15000)
    fun testRallyDialogThemeOverlayAppliesCorrectTypography() {
        lateinit var typography: androidx.compose.material.Typography

        composeTestRule.setContent {
            RallyDialogThemeOverlay {
                typography = MaterialTheme.typography
            }
        }

        try {
            assert(typography.body2.fontWeight == FontWeight.Normal)
            assert(typography.body2.fontSize == 20.sp)
            assert(typography.body2.lineHeight == 28.sp)
            assert(typography.body2.letterSpacing == 1.sp)
            assert(typography.button.fontWeight == FontWeight.Bold)
            assert(typography.button.letterSpacing == 0.2.em)
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    }
}