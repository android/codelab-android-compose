package com.example.compose.rally.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.espresso.IdlingPolicies
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class StatementBodyBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun statementBodyDisplaysCorrectly() {
        val items = listOf("Item1", "Item2", "Item3")
        val colors = { _: String -> Color.Red }
        val amounts = { _: String -> 100f }
        val amountsTotal = 300f
        val circleLabel = "Total"

        composeTestRule.setContent {
            MaterialTheme {
                StatementBody(
                    items = items,
                    colors = colors,
                    amounts = amounts,
                    amountsTotal = amountsTotal,
                    circleLabel = circleLabel
                ) { item ->
                    androidx.compose.material.Text(text = item)
                }
            }
        }

        try {
            composeTestRule.onNodeWithText("Total").assertIsDisplayed()
            composeTestRule.onNodeWithText("300").assertIsDisplayed()
            composeTestRule.onNodeWithText("Item1").assertIsDisplayed()
            composeTestRule.onNodeWithText("Item2").assertIsDisplayed()
            composeTestRule.onNodeWithText("Item3").assertIsDisplayed()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun statementBodyDisplaysEmptyList() {
        val items = emptyList<String>()
        val colors = { _: String -> Color.Red }
        val amounts = { _: String -> 0f }
        val amountsTotal = 0f
        val circleLabel = "Empty"

        composeTestRule.setContent {
            MaterialTheme {
                StatementBody(
                    items = items,
                    colors = colors,
                    amounts = amounts,
                    amountsTotal = amountsTotal,
                    circleLabel = circleLabel
                ) { item ->
                    androidx.compose.material.Text(text = item)
                }
            }
        }

        try {
            composeTestRule.onNodeWithText("Empty").assertIsDisplayed()
            composeTestRule.onNodeWithText("0").assertIsDisplayed()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun statementBodyDisplaysLargeAmounts() {
        val items = listOf("Item1")
        val colors = { _: String -> Color.Green }
        val amounts = { _: String -> 1000000f }
        val amountsTotal = 1000000f
        val circleLabel = "Large Amount"

        composeTestRule.setContent {
            MaterialTheme {
                StatementBody(
                    items = items,
                    colors = colors,
                    amounts = amounts,
                    amountsTotal = amountsTotal,
                    circleLabel = circleLabel
                ) { item ->
                    androidx.compose.material.Text(text = item)
                }
            }
        }

        try {
            composeTestRule.onNodeWithText("Large Amount").assertIsDisplayed()
            composeTestRule.onNodeWithText("1,000,000").assertIsDisplayed()
            composeTestRule.onNodeWithText("Item1").assertIsDisplayed()
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