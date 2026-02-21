# Authorization API Samples

## SpotifyAuthManager

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager

val auth = SpotifyAuthManager(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET", // PKCE onlyなら不要
    redirectUri = "your.app://callback"
)

val pkce = auth.startPkceAuthorization(scope = listOf("user-read-email"))
val token = auth.completePkceAuthorizationFromRedirectUri("your.app://callback?code=...&state=...")

val byCode = auth.exchangeAuthorizationCode("authorization-code")
val byPkceCode = auth.exchangeAuthorizationCodeWithPkce("authorization-code", "code-verifier")

val clientCredentials = auth.requestClientCredentialsToken()
val refreshed = auth.refreshAccessToken()
val accessToken = auth.getValidAccessToken()
```

## AuthorizationApis (low-level)

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.AuthorizationApis

val apis = AuthorizationApis()

val authCodeWithPkceUri = apis.buildAuthorizationCodeWithPkceUri(
    clientId = "YOUR_CLIENT_ID",
    redirectUri = "your.app://callback",
    codeChallenge = "challenge"
)

val authCodeUri = apis.buildAuthorizationCodeUri(
    clientId = "YOUR_CLIENT_ID",
    redirectUri = "https://your.server/callback"
)

val pkceToken = apis.requestAuthorizationCodeWithPkceToken(
    clientId = "YOUR_CLIENT_ID",
    code = "code",
    redirectUri = "your.app://callback",
    codeVerifier = "verifier"
)

val authCodeToken = apis.requestAuthorizationCodeToken(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET",
    code = "code",
    redirectUri = "https://your.server/callback"
)

val ccToken = apis.requestClientCredentialsToken(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET"
)

val refreshPkce = apis.refreshTokenWithPkce(
    clientId = "YOUR_CLIENT_ID",
    refreshToken = "refresh-token"
)

val refresh = apis.refreshToken(
    clientId = "YOUR_CLIENT_ID",
    clientSecret = "YOUR_CLIENT_SECRET",
    refreshToken = "refresh-token"
)
```

## Flow Samples

- [Auth to API Call](../flows/auth-to-api.md)
- [Authorization Code to API](../flows/authorization-code-to-api.md)
- [Client Credentials to API](../flows/client-credentials-to-api.md)
- [Refresh Token](../flows/refresh-token.md)

