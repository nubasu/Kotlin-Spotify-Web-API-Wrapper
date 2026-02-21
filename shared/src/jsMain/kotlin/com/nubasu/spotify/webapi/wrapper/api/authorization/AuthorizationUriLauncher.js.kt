package com.nubasu.spotify.webapi.wrapper.api.authorization

import kotlinx.browser.window

/**
 * Launches launchAuthorizationUriOnPlatform.
 *
 * @param authorizationUri The authorizationUri parameter.
 * @return True when the operation succeeds; otherwise false.
 */
internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean =
    runCatching {
        window.open(url = authorizationUri, target = "_self")
        true
    }.getOrElse { false }
