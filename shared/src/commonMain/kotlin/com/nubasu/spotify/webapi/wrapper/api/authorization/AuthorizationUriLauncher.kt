package com.nubasu.spotify.webapi.wrapper.api.authorization

/**
 * Launches a Spotify authorization URL on the current platform.
 *
 * @param authorizationUri Spotify Accounts authorization URL to open.
 * @return True when the operation succeeds.
 */
internal expect fun launchAuthorizationUriOnPlatform(authorizationUri: String): Boolean
