/*
 * Copyright 2021 The Android Open Source Project
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

package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RallyNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost() {
        // Check that the displayed screen is Overview
        composeTestRule.onNodeWithContentDescription("Overview Screen").assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_callingNavigate() {
        // Navigate to Accounts screen using navController
        runBlocking {
            withContext(Dispatchers.Main) { // Needs to run on the UI tread
                navController.navigate(RallyScreen.Accounts.name)
            }
        }
        // Check that the displayed screen is Accounts
        composeTestRule.onNodeWithContentDescription("Accounts Screen").assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_viaUI() {
        // Click on "All Accounts"
        composeTestRule.onNodeWithContentDescription("All Accounts").performClick()
        // Check that the displayed screen is Accounts
        composeTestRule.onNodeWithContentDescription("Accounts Screen").assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToBills_viaUI() {
        // Click on "Bills"
        composeTestRule.onNodeWithContentDescription("All Bills").apply {
            performScrollTo()
            performClick()
        }
        val route = navController.currentDestination?.route
        assertEquals(route, "Bills")
    }
}
