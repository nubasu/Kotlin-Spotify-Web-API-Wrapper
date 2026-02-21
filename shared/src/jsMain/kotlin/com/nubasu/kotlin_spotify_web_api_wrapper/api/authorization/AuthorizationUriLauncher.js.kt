package com.nubasu.spotify.webapi.wrapper.api.authorization

import kotlinx.browser.window

internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean {
    return runCatching {
        window.open(url = authorizationUri, target = "_self")
        true
    }.getOrElse { false }
}
