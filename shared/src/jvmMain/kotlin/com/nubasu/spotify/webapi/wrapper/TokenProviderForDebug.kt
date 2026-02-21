package com.nubasu.spotify.webapi.wrapper

import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import java.io.File
import java.util.Properties

fun installDebugTokenFromLocalProperties() {
    TokenHolder.tokenProvider = {
        val f = File("../local.properties")
        if (!f.exists()) null
        val p = Properties().apply { f.inputStream().use { load(it) } }
        p.getProperty("spotify.token") ?: p.getProperty("SPOTIFY_TOKEN")
    }
}
