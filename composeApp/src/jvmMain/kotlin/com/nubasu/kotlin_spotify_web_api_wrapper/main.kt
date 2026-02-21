package com.nubasu.kotlin_spotify_web_api_wrapper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kotlin_spotify_web_api_wrapper",
    ) {
        installDebugTokenFromLocalProperties()
        val desktopCallbackCoordinator = remember { JvmDesktopCallbackCoordinator() }
        DisposableEffect(Unit) {
            onDispose { desktopCallbackCoordinator.close() }
        }
        App(desktopCallbackCoordinator = desktopCallbackCoordinator)
    }
}
