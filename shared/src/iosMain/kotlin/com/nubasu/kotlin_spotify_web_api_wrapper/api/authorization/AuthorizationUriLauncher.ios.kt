package com.nubasu.spotify.webapi.wrapper.api.authorization

import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean {
    val url = NSURL.URLWithString(authorizationUri) ?: return false
    val app = UIApplication.sharedApplication
    val root = app.keyWindow?.rootViewController
    val presenter = findTopViewController(root)

    if (presenter != null) {
        val safari = SFSafariViewController(uRL = url)
        presenter.presentViewController(
            viewControllerToPresent = safari,
            animated = true,
            completion = null,
        )
        return true
    }

    return app.openURL(url)
}

private fun findTopViewController(root: UIViewController?): UIViewController? {
    var current = root ?: return null
    while (current.presentedViewController != null) {
        current = current.presentedViewController!!
    }
    return current
}
