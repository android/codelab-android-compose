/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.examples

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.state.databinding.ActivityHelloCodelabBinding

/**
 * An example showing unstructured state stored in an Activity.
 */
class HelloCodelabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloCodelabBinding
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloCodelabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // doAfterTextChange is an event that modifies state
        binding.textInput.doAfterTextChanged { text ->
            name = text.toString()
            updateHello()
        }
    }

    /**
     * This function updates the screen to show the current state of [name]
     */
    private fun updateHello() {
        binding.helloText.text = "Hello, $name"
    }
}

/**
 * A ViewModel extracts _state_ from the UI and defines _events_ that can update it.
 */
class HelloViewModel : ViewModel() {

    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    // onNameChanged is an event we're defining that the UI can invoke
    // (events flow up from UI)
    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

/**
 * An example showing unidirectional data flow in the View system using a ViewModel.
 */
class HelloCodeLabActivityWithViewModel : AppCompatActivity() {
    private val helloViewModel by viewModels<HelloViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHelloCodelabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // doAfterTextChange is an event that triggers an event on the ViewModel
        binding.textInput.doAfterTextChanged {
            // onNameChanged is an event on the ViewModel
            helloViewModel.onNameChanged(it.toString())
        }
        // [helloViewModel.name] is state that we observe to update the UI
        helloViewModel.name.observe(this) { name ->
            binding.helloText.text = "Hello, $name"
        }
    }
}

class HelloActivityCompose : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloScreen()
        }
    }
}

@Composable
private fun HelloScreen(helloViewModel: HelloViewModel = viewModel()) {
    // helloViewModel follows the Lifecycle as the the Activity or Fragment that calls this
    // composable function.

    // name is the _current_ value of [helloViewModel.name]
    val name: String by helloViewModel.name.observeAsState("")

    HelloInput(name = name, onNameChange = { helloViewModel.onNameChanged(it) })
}

@Composable
private fun HelloScreenWithInternalState() {
    val (name, setName) = remember { mutableStateOf("") }
    HelloInput(name = name, onNameChange = setName)
}

/**
 * @param name (state) current text to display
 * @param onNameChange (event) request that text change
 */
@Composable
private fun HelloInput(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column {
        Text(name)
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}
