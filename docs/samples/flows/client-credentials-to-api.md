# Flow Sample: Client Credentials to API Call

このサンプルは、Client Credentials Flow でアプリトークンを取得し、検索 API を呼ぶ流れです。

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.kotlin_spotify_web_api_wrapper.api.search.SearchApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.search.SearchType
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData

suspend fun clientCredentialsFlow() {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        clientSecret = "YOUR_CLIENT_SECRET"
    )

    val token = auth.requestClientCredentialsToken()
    println("tokenType=${token.tokenType}")

    val searchApis = SearchApis()
    val response = searchApis.searchForItem(
        q = "Daft Punk",
        types = setOf(SearchType.ARTIST)
    )

    when (val data = response.data) {
        is SpotifyResponseData.Success -> {
            val first = data.value.artists?.items?.firstOrNull()
            println("artist=${first?.name}")
        }
        is SpotifyResponseData.Error -> println("error=${data.value.error.message}")
    }
}
```

## Notes

- Client Credentials Flow では通常 `refresh_token` は返りません。
- ユーザー権限が必要な API には利用できません。
