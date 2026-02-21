package com.nubasu.spotify.webapi.wrapper.api.authorization

internal expect fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean
