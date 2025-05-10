package com.example.compose.rally.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
//import androidx.compose.ui.test.onAllNodes
import androidx.test.espresso.IdlingPolicies
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.compose.rally.RallyScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale
import java.util.concurrent.TimeUnit
import org.junit.Ignore



@RunWith(AndroidJUnit4::class)
class RallyTopAppBarBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun testRallyTopAppBarDisplaysAllScreens() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyTopAppBar(
                    allScreens = RallyScreen.entries.toList(),
                    onTabSelected = {},
                    currentScreen = RallyScreen.Overview
                )
            }
        }

        RallyScreen.entries.forEach { screen ->
            try {
                composeTestRule.onNodeWithContentDescription(screen.name).assertExists()
            } catch (e: AssertionError) {
                throw AssertionError(e.message + " Current tree: " + getRootTrees())
            }
        }
    }

    @Test(timeout = 15000)
    fun testRallyTopAppBarSelectsCurrentScreen() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyTopAppBar(
                    allScreens = RallyScreen.entries.toList(),
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
                )
            }
        }

        try {
            composeTestRule.onNodeWithContentDescription("Accounts").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyTopAppBarInvokesSelectorOnClick() {
        var selectedScreen: RallyScreen? = null

        composeTestRule.setContent {
            MaterialTheme {
                RallyTopAppBar(
                    allScreens = RallyScreen.entries.toList(),
                    onTabSelected = { selectedScreen = it },
                    currentScreen = RallyScreen.Overview
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Bills").performClick()

        assert(selectedScreen == RallyScreen.Bills)
    }

    @Test(timeout = 15000)
    fun testRallyTabDisplaysIconAndTextWhenSelected() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyTopAppBar(
                    allScreens = listOf(RallyScreen.Overview),
                    onTabSelected = {},
                    currentScreen = RallyScreen.Overview
                )
            }
        }

        try {
            composeTestRule.onNodeWithContentDescription("Overview").assertExists()
            composeTestRule.onNodeWithText("OVERVIEW").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyTabDisplaysOnlyIconWhenNotSelected() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyTopAppBar(
                    allScreens = listOf(RallyScreen.Overview, RallyScreen.Accounts),
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
                )
            }
        }

        try {
            composeTestRule.onNodeWithContentDescription("Overview").assertExists()
            composeTestRule.onNodeWithText("OVERVIEW").assertDoesNotExist()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testRallyTabUppercasesText() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyTopAppBar(
                    allScreens = listOf(RallyScreen.Overview),
                    onTabSelected = {},
                    currentScreen = RallyScreen.Overview
                )
            }
        }

        try {
            composeTestRule.onNodeWithText("OVERVIEW").assertExists()
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

