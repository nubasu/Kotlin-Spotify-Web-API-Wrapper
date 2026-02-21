# Getting Started

This guide shows the shortest path to "authenticate and call an API".

## 1. Add Dependency

If you are using this within the same repository, add the `shared` module as a dependency.

```kotlin
dependencies {
    implementation(projects.shared)
}
```

## 2. Choose an Authorization Flow

- User authorization required: PKCE / Authorization Code Flow
- Server-to-server or app-only permission: Client Credentials Flow

Detailed samples:

- [PKCE -> API](../samples/flows/auth-to-api.md)
- [Authorization Code -> API](../samples/flows/authorization-code-to-api.md)
- [Client Credentials -> API](../samples/flows/client-credentials-to-api.md)

## 3. Call an API

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

## 4. Use Paging and Retry

- [Paging / Rate Limit / Retry](paging-rate-limit-retry.md)
- [Response / Error Handling](response-and-error-handling.md)