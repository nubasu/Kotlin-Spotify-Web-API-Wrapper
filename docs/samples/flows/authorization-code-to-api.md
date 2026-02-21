# Flow Sample: Authorization Code to API Call

このサンプルは、Authorization Code Flow（client secret あり）でトークン取得後に API を呼ぶ流れです。

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.kotlin_spotify_web_api_wrapper.api.users.UsersApis
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import io.ktor.http.Url

suspend fun authorizationCodeFlow(redirectedUri: String) {
    val auth = SpotifyAuthManager(
        clientId = "YOUR_CLIENT_ID",
        clientSecret = "YOUR_CLIENT_SECRET",
        redirectUri = "https://your.server/callback"
    )

    val state = "server-side-state"
    val authorizationUri = auth.buildAuthorizationCodeUriAndLaunch(
        scope = listOf("user-read-email", "user-read-private"),
        state = state
    )
    println("Opened: $authorizationUri")

    val callback = Url(redirectedUri)
    val code = callback.parameters["code"] ?: error("Authorization code is missing.")
    val returnedState = callback.parameters["state"]
    check(returnedState == state) { "State mismatch." }

    auth.exchangeAuthorizationCode(code)

    val usersApis = UsersApis()
    val me = usersApis.getCurrentUsersProfile()
    when (val data = me.data) {
        is SpotifyResponseData.Success -> println("displayName=${data.value.displayName}")
        is SpotifyResponseData.Error -> println("error=${data.value.error.message}")
    }
}
```
