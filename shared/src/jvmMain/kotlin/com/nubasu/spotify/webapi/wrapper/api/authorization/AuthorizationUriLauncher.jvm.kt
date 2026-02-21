package com.nubasu.spotify.webapi.wrapper.api.authorization

import java.awt.Desktop
import java.net.URI

/**
 * Launches launchAuthorizationUriOnPlatform.
 *
 * @param authorizationUri The authorizationUri parameter.
 * @return True when the operation succeeds; otherwise false.
 */
internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean {
    return runCatching {
        if (!Desktop.isDesktopSupported()) return false
        val desktop = Desktop.getDesktop()
        if (!desktop.isSupported(Desktop.Action.BROWSE)) return false
        desktop.browse(URI(authorizationUri))
        true
    }.getOrElse { false }
}
