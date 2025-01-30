package com.example.compose.rally

import androidx.compose.material.MaterialTheme
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
class RallyScreenBaseRockGeneratedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS)
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
    }

    @Test(timeout = 15000)
    fun testOverviewScreenContent() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyScreen.Overview.content {}
            }
        }

        try {
            composeTestRule.onNodeWithText("Alerts").assertExists()
            composeTestRule.onAllNodesWithText("SEE ALL")[0].assertExists()
            composeTestRule.onAllNodesWithText("SEE ALL")[1].assertExists()
            composeTestRule.onAllNodesWithText("SEE ALL")[2].assertExists()
            composeTestRule.onNodeWithText("Accounts").assertExists()
            composeTestRule.onNodeWithText("Bills").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testAccountsScreenContent() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyScreen.Accounts.content {}
            }
        }

        try {
            composeTestRule.onNodeWithContentDescription("Checking account ending in 1234, current balance $ 2,215.13").assertExists()
            composeTestRule.onNodeWithContentDescription("Home Savings account ending in 5678, current balance $ 8,676.88").assertExists()
            composeTestRule.onNodeWithContentDescription("Car Savings account ending in 9012, current balance $ 987.48").assertExists()
            composeTestRule.onNodeWithContentDescription("Vacation account ending in 3456, current balance $ 253").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testBillsScreenContent() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyScreen.Bills.content {}
            }
        }

        try {
            composeTestRule.onNodeWithContentDescription("RedPay Credit account ending in n 29, current balance –$ 45.36").assertExists()
            composeTestRule.onNodeWithContentDescription("Rent account ending in eb 9, current balance –$ 1,200").assertExists()
            composeTestRule.onNodeWithContentDescription("TabFine Credit account ending in b 22, current balance –$ 87.33").assertExists()
            composeTestRule.onNodeWithContentDescription("ABC Loans account ending in b 29, current balance –$ 400").assertExists()
        } catch (e: AssertionError) {
            throw AssertionError(e.message + " Current tree: " + getRootTrees())
        }
    }

    @Test(timeout = 15000)
    fun testScreenIconsExist() {
        composeTestRule.setContent {
            MaterialTheme {
                RallyScreen.entries.forEach { screen ->
                    screen.icon
                }
            }
        }

        try {
            composeTestRule.onRoot().assertExists()
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