# Flow Sample: Refresh Token

This sample shows how to refresh an access token using a refresh token.

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager

suspend fun refreshFlow(code: String) {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        clientSecret = "YOUR_CLIENT_SECRET", // optional when using PKCE only
        redirectUri = "https://your.server/callback"
    )

    // Initial token acquisition (Authorization Code Flow example)
    val initial = auth.exchangeAuthorizationCode(code)
    println("Initial access token: ${initial.accessToken}")
    println("Has refresh token: ${initial.refreshToken != null}")

    // 1) Explicit refresh
    val refreshed = auth.refreshAccessToken()
    println("Refreshed access token: ${refreshed.accessToken}")

    // 2) Expiration check + auto-refresh
    val validAccessToken = auth.getValidAccessToken(
        leewaySeconds = 60,
        autoRefresh = true
    )
    println("Valid access token: $validAccessToken")
}
```

## Notes

- Tokens from `Client Credentials Flow` usually do not include `refresh_token`.
- `refreshAccessToken()` updates internal token state (`getCurrentToken()`).