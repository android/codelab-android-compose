package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.IdlingPolicies
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RallyActivityBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun testRallyAppInitialState() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Overview").assertIsSelected()
            composeTestRule.onNodeWithContentDescription("Accounts").assertExists()
            composeTestRule.onNodeWithContentDescription("Bills").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAppTabNavigation() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Accounts").performClick()
            composeTestRule.onNodeWithContentDescription("Accounts").assertExists()

            composeTestRule.onNodeWithContentDescription("Bills").performClick()
            composeTestRule.onNodeWithContentDescription("Bills").assertExists()

            composeTestRule.onNodeWithContentDescription("Overview").performClick()
            composeTestRule.onNodeWithContentDescription("Overview").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAppOverviewScreen() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Overview").assertIsSelected()
            composeTestRule.onNodeWithText("Accounts").assertExists()
            composeTestRule.onNodeWithText("Bills").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAppAccountsScreen() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Accounts").performClick()
            composeTestRule.onNodeWithText("Accounts").assertExists()
            composeTestRule.onNodeWithContentDescription("Accounts").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAppBillsScreen() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Bills").performClick()
            composeTestRule.onNodeWithText("Bills").assertExists()
            composeTestRule.onNodeWithContentDescription("Bills").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAppTopBarVisibility() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Overview").assertExists()
            composeTestRule.onNodeWithContentDescription("Accounts").assertExists()
            composeTestRule.onNodeWithContentDescription("Bills").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyAppScreenContentChanges() {
        composeTestRule.setContent {
            RallyApp()
        }

        try {
            composeTestRule.onNodeWithContentDescription("Overview").assertExists()
            composeTestRule.onNodeWithContentDescription("Accounts").performClick()
            composeTestRule.onNodeWithContentDescription("Accounts").assertExists()
            composeTestRule.onNodeWithContentDescription("Bills").performClick()
            composeTestRule.onNodeWithContentDescription("Bills").assertExists()
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