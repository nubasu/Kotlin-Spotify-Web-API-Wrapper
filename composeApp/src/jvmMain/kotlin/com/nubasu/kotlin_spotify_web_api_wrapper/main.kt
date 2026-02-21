package com.nubasu.kotlin_spotify_web_api_wrapper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kotlin_spotify_web_api_wrapper",
    ) {
        installDebugTokenFromLocalProperties()
        TokenHolder.getTokenFromProvider()
        App()
    }
}
