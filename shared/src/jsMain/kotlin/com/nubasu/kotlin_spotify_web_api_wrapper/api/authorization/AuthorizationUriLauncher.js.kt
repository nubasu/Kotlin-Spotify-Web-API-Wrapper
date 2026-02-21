package com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization

import kotlinx.browser.window

internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean {
    return runCatching {
        window.open(url = authorizationUri, target = "_self")
        true
    }.getOrElse { false }
}
