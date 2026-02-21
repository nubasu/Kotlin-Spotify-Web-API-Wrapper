package com.nubasu.spotify.webapi.wrapper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "spotify.webapi.wrapper",
    ) {
        installDebugTokenFromLocalProperties()
        val desktopCallbackCoordinator = remember { JvmDesktopCallbackCoordinator() }
        DisposableEffect(Unit) {
            onDispose { desktopCallbackCoordinator.close() }
        }
        App(desktopCallbackCoordinator = desktopCallbackCoordinator)
    }
}
