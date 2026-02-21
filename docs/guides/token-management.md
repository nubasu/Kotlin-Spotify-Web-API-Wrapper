# Token Management

Token management is mainly handled by `SpotifyAuthManager` and `TokenHolder`.

## Recommended: Use `SpotifyAuthManager` and then call APIs

`SpotifyAuthManager` updates `TokenHolder.token` internally when a token is acquired.

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.spotify.webapi.wrapper.api.users.UsersApis

suspend fun callWithAuthManager(code: String) {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        clientSecret = "YOUR_CLIENT_SECRET",
        redirectUri = "https://your.server/callback"
    )

    auth.exchangeAuthorizationCode(code)
    val usersApi = UsersApis()
    usersApi.getCurrentUsersProfile()
}
```

## Set a Token Explicitly

```kotlin
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder

TokenHolder.token = "YOUR_ACCESS_TOKEN"
```

## Use `tokenProvider`

`tokenProvider` is called only when `TokenHolder.token` is empty.

```kotlin
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder

TokenHolder.tokenProvider = {
    secureStore.loadAccessToken()
}
```

## Notes

- `Client Credentials` usually does not return `refresh_token`.
- `SpotifyAuthManager.clearToken()` also clears `TokenHolder.token`.