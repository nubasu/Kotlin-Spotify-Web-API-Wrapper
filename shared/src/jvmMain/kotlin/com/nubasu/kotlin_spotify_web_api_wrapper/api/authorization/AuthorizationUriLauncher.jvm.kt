package com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization

import java.awt.Desktop
import java.net.URI

internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean {
    return runCatching {
        if (!Desktop.isDesktopSupported()) return false
        val desktop = Desktop.getDesktop()
        if (!desktop.isSupported(Desktop.Action.BROWSE)) return false
        desktop.browse(URI(authorizationUri))
        true
    }.getOrElse { false }
}
