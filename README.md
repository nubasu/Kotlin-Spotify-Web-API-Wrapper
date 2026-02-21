# Spotify Web API Kotlin Multiplatform Wrapper
A Kotlin Multiplatform (KMP) wrapper for the Spotify Web API.

Designed to be type-safe, coroutine-friendly, and easy to use from Kotlin/JVM, Android, iOS, and other KMP targets.

## Status

- Basic API
  - [x] Albums
  - [x] Artists
  - [x] Audiobooks
  - [x] Categories
  - [x] Chapters
  - [x] Episodes
  - [x] Genres
  - [x] Libraries 
  - [x] Markets
  - [x] Player
  - [x] Playlists
  - [x] Search
  - [x] Shows
  - [x] Tracks
  - [x] Users
- Auth API
  - [x] PKCE
  - [x] Client Credentials
  - [x] Authorization Code
  - [x] Refresh
- [x] Paging helpers / Rate limit handling / Retry policies
- [x] Samples + Docs
- [x] Tests

## Documentation

- Docs index: [docs/README.md](docs/README.md)
- API samples by domain: [docs/samples/apis](docs/samples/apis)
- Flow samples: [docs/samples/flows](docs/samples/flows)
  - Auth to API: [docs/samples/flows/auth-to-api.md](docs/samples/flows/auth-to-api.md)
  - Authorization Code to API: [docs/samples/flows/authorization-code-to-api.md](docs/samples/flows/authorization-code-to-api.md)
  - Client Credentials to API: [docs/samples/flows/client-credentials-to-api.md](docs/samples/flows/client-credentials-to-api.md)
  - Refresh token: [docs/samples/flows/refresh-token.md](docs/samples/flows/refresh-token.md)
- Getting started: [docs/guides/getting-started.md](docs/guides/getting-started.md)
- Response/error handling: [docs/guides/response-and-error-handling.md](docs/guides/response-and-error-handling.md)
- Paging/RateLimit/Retry guide: [docs/guides/paging-rate-limit-retry.md](docs/guides/paging-rate-limit-retry.md)
- Token management: [docs/guides/token-management.md](docs/guides/token-management.md)
- Troubleshooting: [docs/guides/troubleshooting.md](docs/guides/troubleshooting.md)

## Sample App (composeApp)

- A sample app is included in `composeApp/`.
- It supports: `PKCE auth -> load current user's playlists -> start/pause playback`.
- Android callback deep link: `spotifyauth://callback` (configure this in Spotify Dashboard redirect URIs).
- JVM/Desktop auto callback: `http://127.0.0.1:8888/callback` (configure this in Spotify Dashboard redirect URIs).
- Desktop/JVM: run `./gradlew :composeApp:run`.

## Use From Maven Central

### `build.gradle.kts` (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.nubasu:kotlin-spotify-web-api-wrapper:0.1.0")
}
```

### `build.gradle` (Groovy DSL)

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.nubasu:kotlin-spotify-web-api-wrapper:0.1.0'
}
```

## Quick Start

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.spotify.webapi.wrapper.api.albums.AlbumsApis

val auth = SpotifyAuthManager(
    clientId = "YOUR_CLIENT_ID",
    redirectUri = "your.app://callback"
)

val pkce = auth.startPkceAuthorizationAndLaunch(scope = listOf("user-read-email"))
// Android/iOS: prefer in-app authentication UI, otherwise open the browser

val albumsApi = AlbumsApis()
val album = albumsApi.getAlbum("album-id")
```

## References

- Spotify Web API reference: https://developer.spotify.com/documentation/web-api/reference
- Spotify Authorization concepts: https://developer.spotify.com/documentation/web-api/concepts/authorization

## Contributing

PRs/issues are welcome.

- If you find a bug: open an issue with steps to reproduce.
- If you want to add an endpoint: follow existing conventions and include tests.

