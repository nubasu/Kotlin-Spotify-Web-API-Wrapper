package com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

internal actual fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean {
    val context = resolveApplication() ?: return false
    val uri = Uri.parse(authorizationUri)

    return runCatching {
        val customTabs = CustomTabsIntent.Builder().setShowTitle(true).build()
        customTabs.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        customTabs.launchUrl(context, uri)
        true
    }.getOrElse {
        runCatching {
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            true
        }.getOrElse { false }
    }
}

private fun resolveApplication(): Application? {
    return runCatching {
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val currentApplication = activityThreadClass.getMethod("currentApplication")
        currentApplication.invoke(null) as? Application
    }.getOrNull()
}
