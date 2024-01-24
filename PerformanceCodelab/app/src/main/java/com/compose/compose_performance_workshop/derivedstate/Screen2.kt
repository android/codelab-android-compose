package com.compose.compose_performance_workshop.derivedstate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen2() {
    Column(Modifier.fillMaxSize()) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        val name = "$firstName $lastName"

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = { Text("First Name") })
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = { Text("Last Name") })

        Spacer(modifier = Modifier.size(16.dp))

        Text("Name: $name", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.size(16.dp))

        // Only show this button if there is text entered
        AnimatedVisibility(visible = name.isNotBlank()) {
            Button(
                onClick = {
                    firstName = ""
                    lastName = ""
                }
            ) {
                Text("Clear")
            }
        }
    }
}