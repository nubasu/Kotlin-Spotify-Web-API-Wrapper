# Spotify Web API Kotlin Multiplatform Wrapper

> ⚠️ **In Progress / WIP**
>
> This library is currently under active development. APIs, package structure, and behavior may change without notice.
> Contributions and feedback are welcome.

A Kotlin Multiplatform (KMP) wrapper for the [**Spotify Web API**](https://developer.spotify.com/documentation/web-api).

Designed to be type-safe, coroutine-friendly, and easy to use from Kotlin/JVM, Android, iOS, and other KMP targets.

## Status

- 🚧 **In Progress (WIP)**
- Basic API
  - [x] Albums
  - [x] Artists
  - [x] Audiobooks
  - [x] Categories
  - [x] Chapters
  - [x] Episodes
  - [x] Genres
  - [x] Markets
  - [x] Player
  - [x] Playlists
  - [x] Search
  - [x] Shows
  - [x] Tracks 
  - [x] Users
- Auth API
  - [x] PKCE
  - [x] Client Credentials
  - [x] Authorization Code
  - [x] Refresh
- [x] Paging helpers / Rate limit handling / Retry policies
- [ ] Samples + Docs
- [ ] Publish artifacts
- [x] Tests

## Contributing

PRs/issues are welcome.
 - If you find a bug: open an Issue with steps to reproduce.
 - If you want to add an endpoint: please follow existing conventions and include tests if possible.

## Authentication

### 1) Authorization Code with PKCE (recommended for apps)

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager

val auth = SpotifyAuthManager(
    clientId = "YOUR_CLIENT_ID",
    redirectUri = "your.app://callback"
)

// Step 1: Start auth and open this URL in browser/webview
val pkce = auth.startPkceAuthorization(
    scope = listOf("user-read-email", "user-read-private")
)
val authorizationUrl = pkce.authorizationUri

// Step 2-A: If you received full redirect URI
val token = auth.completePkceAuthorizationFromRedirectUri(redirectedUri)

// Step 2-B: If your framework gives code/state separately
val token2 = auth.completePkceAuthorization(
    code = code,
    returnedState = state
)

// TokenHolder.token is set automatically
```

### 2) Authorization Code Flow (server-side)

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager

val auth = SpotifyAuthManager(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET",
    redirectUri = "https://your.server/callback"
)

// Build authorize URL
val authorizationUrl = auth.buildAuthorizationCodeUri(
    scope = listOf("user-read-email", "playlist-read-private"),
    state = "csrf-token"
)

// Exchange code from callback
val token = auth.exchangeAuthorizationCode(code)
```

### 3) Client Credentials Flow (server-to-server)

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager

val auth = SpotifyAuthManager(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET"
)

val token = auth.requestClientCredentialsToken()
```

### 4) Refresh Token

```kotlin
// Refresh explicitly
val refreshed = auth.refreshAccessToken()

// Or use auto refresh when token is near expiry
val accessToken = auth.getValidAccessToken()
```

### 5) Low-level API (manual control)

If you need custom handling, use `AuthorizationApis` directly:

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.AuthorizationApis

val apis = AuthorizationApis()
val token = apis.requestClientCredentialsToken(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET"
)
```

## Paging Helpers

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.PagingHelpers

val first = albumsApis.getUsersSavedAlbums()
val allItemsResponse = PagingHelpers.collectAllItems(
    firstPageResponse = first,
    nextUrlSelector = { it.next },
    itemsSelector = { it.items },
    fetchNextPage = { paging -> albumsApis.getUsersSavedAlbums(pagingOptions = paging) }
)
```

## Rate Limit Handling / Retry Policies

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.RetryPolicy
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.SpotifyRetryExecutor

val executor = SpotifyRetryExecutor(
    retryPolicy = RetryPolicy(
        maxRetries = 3,
        baseDelayMillis = 500,
        retryStatusCodes = setOf(429, 500, 502, 503, 504),
        respectRetryAfterHeader = true
    )
)

val response = executor.execute {
    playlistsApis.getPlaylist("playlist-id")
}
```
