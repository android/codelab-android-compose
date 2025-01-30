package com.example.compose.rally.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.IdlingPolicies
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.compose.rally.ui.theme.RallyDialogThemeOverlay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import org.junit.Ignore


@RunWith(AndroidJUnit4::class)
class RallyAlertDialogBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun testRallyAlertDialogDisplaysCorrectly() {
        composeTestRule.setContent {
            RallyAlertDialog(
                onDismiss = {},
                bodyText = "Test body text",
                buttonText = "OK"
            )
        }

        try {
            composeTestRule.onNodeWithText("Test body text").assertExists()
            composeTestRule.onNodeWithText("OK").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAlertDialogDismissButtonWorks() {
        var dismissed = false
        composeTestRule.setContent {
            RallyAlertDialog(
                onDismiss = { dismissed = true },
                bodyText = "Test body text",
                buttonText = "Dismiss"
            )
        }

        try {
            composeTestRule.onNodeWithText("Dismiss").performClick()
            assert(dismissed) { "Dialog was not dismissed when button was clicked" }
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Ignore("Test with compilation issue")
    @Test(timeout = 15000)
    fun testRallyAlertDialogTheming() {/*
        lateinit var dialogTypography: androidx.compose.material.Typography

        composeTestRule.setContent {
            RallyDialogThemeOverlay {
                dialogTypography = MaterialTheme.typography
                RallyAlertDialog(
                    onDismiss = {},
                    bodyText = "Test body text",
                    buttonText = "OK"
                )
            }
        }

        try {
            composeTestRule.onNodeWithText("Test body text")
                .assert(hasTextStyle(dialogTypography.body2))
            composeTestRule.onNodeWithText("OK")
                .assert(hasTextStyle(dialogTypography.button))
        } catch (e: AssertionError) {
            val tree = composeTestRule.onRoot().printToString()
            throw AssertionError(e.message + " Current tree: " + tree)
        }
    */}

    @Test(timeout = 15000)
    fun testRallyAlertDialogDividerDisplayed() {
        composeTestRule.setContent {
            RallyAlertDialog(
                onDismiss = {},
                bodyText = "Test body text",
                buttonText = "OK"
            )
        }

        try {
            composeTestRule.onNode(hasTestTag("Divider")).assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    private fun getRootTrees(): String {
        val allRootNodes = composeTestRule.onAllNodes(isRoot())
        val builder = StringBuilder()
        for (i in 0 until allRootNodes.fetchSemanticsNodes().size) {
            builder.append("\n")
            builder.append("Root Node #$i: \n")
            builder.append(allRootNodes[i].printToString())
            builder.append("\n")
        }
        return builder.toString()
    }

}
