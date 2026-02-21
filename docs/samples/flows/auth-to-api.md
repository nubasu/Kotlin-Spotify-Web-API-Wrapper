# Flow Sample: Auth to API Call

This sample shows the shortest flow to start PKCE authorization, process the callback, and call any API.

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.albums.AlbumsApis
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData

suspend fun authThenCallApi(redirectedUri: String) {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        redirectUri = "your.app://callback"
    )

    // 1) Build authorization URL and launch automatically
    //    Android/iOS: prefer in-app authentication UI
    //    Others: open browser
    val request = auth.startPkceAuthorizationAndLaunch(
        scope = listOf("user-read-email", "user-read-private")
    )
    val authorizationUri = request.authorizationUri
    println("Opened: $authorizationUri")

    // 2) Exchange token from callback URI
    val token = auth.completePkceAuthorizationFromRedirectUri(redirectedUri)
    println("Access token acquired: ${token.accessToken.isNotBlank()}")

    // 3) Call API
    val albumsApi = AlbumsApis()
    val response = albumsApi.getAlbum("4aawyAB9vmqN3uQ7FjRGTy")

    when (val data = response.data) {
        is SpotifyResponseData.Success -> println("Album: ${data.value.name}")
        is SpotifyResponseData.Error -> println("Error: ${data.value.error.message}")
    }
}
```

## Notes

- `completePkceAuthorization...` automatically sets `TokenHolder.token` on success.
- API classes (`AlbumsApis`, etc.) send Bearer tokens via `TokenHolder`.
- For explicit control, use `startPkceAuthorization()` + `launchAuthorizationInAppOrBrowser(uri)`.