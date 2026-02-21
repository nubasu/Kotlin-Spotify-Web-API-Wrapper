package com.nubasu.spotify.webapi.wrapper

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

/**
 * Retrieves data for getPlatform.
 *
 * @return The resulting Platform value.
 */
actual fun getPlatform(): Platform = AndroidPlatform()
