package com.codelabs.state.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.state.ui.theme.BasicStateCodelabTheme

private fun getWellnessTasks() = List(30) { WellnessTask(it, "Task # $it") }

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulWaterCounter(modifier)

        val list = remember {
            getWellnessTasks().toMutableStateList()
        }
        WellnessTaskList(
            list = list,
            onCloseTask = { task -> list.remove(task) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}
