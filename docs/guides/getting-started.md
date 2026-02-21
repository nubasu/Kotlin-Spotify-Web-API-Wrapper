# Getting Started

このガイドは、最短で「認証して API を呼ぶ」までの手順です。

## 1. 依存関係を追加

同一リポジトリ内で使う場合は `shared` モジュールを依存に追加します。

```kotlin
dependencies {
    implementation(projects.shared)
}
```

## 2. 認証フローを選ぶ

- ユーザー操作が必要: PKCE / Authorization Code Flow
- サーバー間連携やアプリ権限のみ: Client Credentials Flow

詳細サンプル:

- [PKCE -> API](../samples/flows/auth-to-api.md)
- [Authorization Code -> API](../samples/flows/authorization-code-to-api.md)
- [Client Credentials -> API](../samples/flows/client-credentials-to-api.md)

## 3. API を呼ぶ

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.albums.AlbumsApis
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData

suspend fun fetchAlbum() {
    val api = AlbumsApis()
    val response = api.getAlbum("4aawyAB9vmqN3uQ7FjRGTy")

    when (val data = response.data) {
        is SpotifyResponseData.Success -> println("album=${data.value.name}")
        is SpotifyResponseData.Error -> println("error=${data.value.error.message}")
    }
}
```

## 4. ページングとリトライを使う

- [Paging / Rate Limit / Retry](paging-rate-limit-retry.md)
- [Response / Error Handling](response-and-error-handling.md)
