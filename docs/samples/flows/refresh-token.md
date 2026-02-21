# Flow Sample: Refresh Token

このサンプルは、refresh token を使ってアクセストークンを更新する流れです。

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager

suspend fun refreshFlow(code: String) {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        clientSecret = "YOUR_CLIENT_SECRET", // PKCEのみなら不要
        redirectUri = "https://your.server/callback"
    )

    // 初回トークン取得（Authorization Code Flow の例）
    val initial = auth.exchangeAuthorizationCode(code)
    println("Initial access token: ${initial.accessToken}")
    println("Has refresh token: ${initial.refreshToken != null}")

    // 1) 明示的に refresh
    val refreshed = auth.refreshAccessToken()
    println("Refreshed access token: ${refreshed.accessToken}")

    // 2) 期限チェック + 自動 refresh
    val validAccessToken = auth.getValidAccessToken(
        leewaySeconds = 60,
        autoRefresh = true
    )
    println("Valid access token: $validAccessToken")
}
```

## Notes

- `Client Credentials Flow` のトークンには通常 `refresh_token` が含まれません。
- `refreshAccessToken()` は内部トークン状態（`getCurrentToken()`）を更新します。

