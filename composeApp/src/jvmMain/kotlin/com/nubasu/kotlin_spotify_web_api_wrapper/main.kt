package com.nubasu.kotlin_spotify_web_api_wrapper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kotlin_spotify_web_api_wrapper",
    ) {
        App()
    }
}