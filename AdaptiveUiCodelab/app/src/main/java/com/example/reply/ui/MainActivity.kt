package com.example.reply.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.theme.ReplyTheme
import com.example.reply.ui.utils.DevicePosture
import com.example.reply.ui.utils.isBookPosture
import com.example.reply.ui.utils.isSeparating
import com.example.reply.ui.utils.isTableTopPosture
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivity : ComponentActivity() {

    private val viewModel: ReplyHomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Flow of [DevicePosture] that emits every time there's a change in the windowLayoutInfo
         */
        val devicePostureFlow =  WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
                when {
                    isTableTopPosture(foldingFeature) ->
                        DevicePosture.TableTopPosture(foldingFeature.bounds)
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)
                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )

        setContent {
            ReplyTheme {
                val windowSize = calculateWindowSizeClass(this)
                val devicePosture = devicePostureFlow.collectAsState().value
                val uiState = viewModel.uiState.collectAsState().value
                ReplyApp(windowSize.widthSizeClass, devicePosture, uiState)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    ReplyTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Compact,
            foldingDevicePosture = DevicePosture.NormalPosture
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun ReplyAppPreviewTablet() {
    ReplyTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Medium,
            foldingDevicePosture = DevicePosture.NormalPosture
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppPreviewDesktop() {
    ReplyTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Expanded,
            foldingDevicePosture = DevicePosture.NormalPosture
        )
    }
}