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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.compose.rally.RallyScreen.Accounts
import com.example.compose.rally.RallyScreen.Bills
import com.example.compose.rally.RallyScreen.Overview
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.accounts.AccountsBody
import com.example.compose.rally.ui.accounts.SingleAccountBody
import com.example.compose.rally.ui.bills.BillsBody
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewBody
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = when (
            backstackEntry.value?.arguments?.getString(KEY_ROUTE)?.substringBefore("/")
        ) {
            Accounts.name -> Accounts
            Bills.name -> Bills
            else -> Overview
        }

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                RallyNavHost(navController)
            }
        }
    }
}

@Composable
fun RallyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Overview.name
    ) {
        composable(Overview.name) {
            OverviewBody(
                onClickSeeAllAccounts = { navController.navigate(Accounts.name) },
                onClickSeeAllBills = { navController.navigate(Bills.name) },
                onAccountClick = { name ->
                    navController.navigate("${Accounts.name}/$name")
                },
            )
        }
        composable(Accounts.name) {
            AccountsBody(accounts = UserData.accounts) { name ->
                navController.navigate("Accounts/$name")
            }
        }
        composable(Bills.name) {
            BillsBody(bills = UserData.bills)
        }
        val accountsName = Accounts.name
        composable(
            route = "$accountsName/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "rally://$accountsName/{name}"
            }),
        ) { entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            SingleAccountBody(account = account)
        }
    }
}
