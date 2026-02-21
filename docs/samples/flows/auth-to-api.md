# Flow Sample: Auth to API Call

このサンプルは、PKCE 認証を開始してコールバック処理し、任意の API を呼ぶまでの最短フローです。

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.albums.AlbumsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData

suspend fun authThenCallApi(redirectedUri: String) {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        redirectUri = "your.app://callback"
    )

    // 1) 認証開始 URL を発行（ブラウザ/WebViewで開く）
    val request = auth.startPkceAuthorization(
        scope = listOf("user-read-email", "user-read-private")
    )
    val authorizationUri = request.authorizationUri
    println("Open: $authorizationUri")

    // 2) コールバック URI からトークン取得
    val token = auth.completePkceAuthorizationFromRedirectUri(redirectedUri)
    println("Access token acquired: ${token.accessToken.isNotBlank()}")

    // 3) API 呼び出し
    val albumsApi = AlbumsApis()
    val response = albumsApi.getAlbum("4aawyAB9vmqN3uQ7FjRGTy")

    when (val data = response.data) {
        is SpotifyResponseData.Success -> println("Album: ${data.value.name}")
        is SpotifyResponseData.Error -> println("Error: ${data.value.error.message}")
    }
}
```

## Notes

- `completePkceAuthorization...` 成功時に `TokenHolder.token` は自動設定されます。
- API クラス（`AlbumsApis` など）は `TokenHolder` を使って Bearer Token を送信します。

