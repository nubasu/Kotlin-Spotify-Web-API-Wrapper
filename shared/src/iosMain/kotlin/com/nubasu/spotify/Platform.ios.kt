package com.nubasu.spotify.webapi.wrapper

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

/**
 * Retrieves data for getPlatform.
 *
 * @return The resulting Platform value.
 */
actual fun getPlatform(): Platform = IOSPlatform()
