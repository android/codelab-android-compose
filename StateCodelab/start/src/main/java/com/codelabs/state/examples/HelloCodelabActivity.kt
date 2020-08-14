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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.ui.tooling.preview.Preview
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
    val helloViewModel by viewModels<HelloViewModel>()

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

@Composable
fun ExpandingCard(title: String, body: String) {
    var expanded by savedInstanceState { false }
    ExpandingCard(
        title = title,
        body = body,
        expanded = expanded,
        onExpand = { expanded = true },
        onCollapse = { expanded = false }
    )
}

@Composable
fun ExpandingCardFirstVersion(title: String, body: String) {
    var expanded by remember { mutableStateOf(false) }

    // describe the card for the current state of expanded
    Card {
        Column(
            Modifier
                .width(280.dp)
                .animateContentSize() // automatically animate size when it changes
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = title)

            // change the content of the card depending on the current value of expanded
            if (expanded) {
                Text(text = body, Modifier.padding(top = 8.dp))
                IconButton(onClick = { expanded = false }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.ExpandLess)
                }
            } else {
                IconButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.ExpandMore)
                }
            }
        }
    }
}

class ExpandingViewModel : ViewModel() {
    var expanded by mutableStateOf(false)
        private set

    var title by mutableStateOf("")
        private set

    var body by mutableStateOf("")
        private set

    init {
        // load title and body from a database
        // see the guide to app architecture for more information about how to load data in a
        // ViewModel
    }

    fun expand() {
        expanded = true
    }

    fun collapse() {
        expanded = false
    }
}

@Composable
fun ExpandingCardWithViewModel(viewModel: ExpandingViewModel = viewModel()) {
    ExpandingCard(
        title = viewModel.title,
        body = viewModel.body,
        expanded = viewModel.expanded,
        onExpand = { viewModel.expand() },
        onCollapse = { viewModel.collapse() }
    )
}

@Composable
fun ExpandingCard(
    title: String,
    body: String,
    expanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit
) {
    Card {
        Column(
            Modifier
                .width(280.dp)
                .animateContentSize() // automatically animate size when it changes
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(title)
            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Text(body)
                IconButton(onClick = onCollapse, Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.ExpandLess)
                }
            } else {
                IconButton(onClick = onExpand, Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.ExpandMore)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExpandingCard() {
    Stack(Modifier.fillMaxSize()) {
        Box(Modifier.gravity(Alignment.Center)) {
            ExpandingCard(
                title = "Title text",
                body =
"""Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eleifend, augue quis fermentum feugiat, neque lacus elementum velit, ut molestie quam ligula at magna. Etiam dictum in nulla a posuere. Integer nisl tortor, mollis id hendrerit quis, tincidunt eget dolor. Nulla tempor leo tellus, ac aliquam nunc ornare sed. Aliquam ut odio rutrum, convallis mi vel, fringilla nibh. Vivamus vel mi rutrum, vehicula metus nec, efficitur risus. Phasellus vel blandit libero. Proin leo mauris, iaculis a eleifend vitae, malesuada a dolor. Suspendisse euismod bibendum sapien tincidunt dapibus. Quisque elit dui, dictum in sem eget, ultricies condimentum ante. Praesent elementum tincidunt mi, at vulputate turpis volutpat non.
"""
            )
        }
    }
}
