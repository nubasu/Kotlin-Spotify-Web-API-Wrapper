# Flow Sample: Authorization Code to API Call

This sample shows how to acquire a token with Authorization Code Flow (with client secret) and then call an API.

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.spotify.webapi.wrapper.api.users.UsersApis
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
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