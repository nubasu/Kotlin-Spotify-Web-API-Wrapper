# Flow Sample: Client Credentials to API Call

This sample shows how to acquire an app token with Client Credentials Flow and call the search API.

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.spotify.webapi.wrapper.api.search.SearchApis
import com.nubasu.spotify.webapi.wrapper.request.search.SearchType
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData

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

- Client Credentials Flow usually does not return `refresh_token`.
- It cannot be used for APIs that require user permissions.