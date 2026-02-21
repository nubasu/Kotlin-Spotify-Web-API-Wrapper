# Token Management

トークン管理は主に `SpotifyAuthManager` と `TokenHolder` で行います。

## 推奨: `SpotifyAuthManager` で取得して API 呼び出し

`SpotifyAuthManager` はトークン取得時に `TokenHolder.token` を内部更新します。

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.kotlin_spotify_web_api_wrapper.api.users.UsersApis

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

## 明示的にトークンを設定

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder

TokenHolder.token = "YOUR_ACCESS_TOKEN"
```

## `tokenProvider` を使う

`TokenHolder.token` が空の場合のみ `tokenProvider` が呼ばれます。

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder

TokenHolder.tokenProvider = {
    secureStore.loadAccessToken()
}
```

## 注意点

- `Client Credentials` は通常 `refresh_token` を返しません。
- `SpotifyAuthManager.clearToken()` は `TokenHolder.token` も消去します。
