# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Compile check
```
./gradlew :shared:compileKotlinJvm
```

### Run tests
```
./gradlew :shared:jvmTest
./gradlew :shared:testDebugUnitTest
./gradlew :shared:jsBrowserTest
```

### Run a single test class (JVM)
```
./gradlew :shared:jvmTest --tests "com.nubasu.spotify.webapi.wrapper.api.AlbumsApisTest"
```

### Lint / format
```
./gradlew ktlintFormat
```

### Run sample app (JVM/Desktop)
```
./gradlew :composeApp:run
```

### Publish (`:shared` only; other subprojects have publish tasks disabled)
Publishing requires Gradle project properties for Maven Central credentials and signing keys (see `gradle.properties`).

## Architecture

### Module layout
- `shared/` — The published KMP library. All Spotify API logic lives here.
- `composeApp/` — Sample Compose Multiplatform app (Android + JVM/Desktop). Not published.
- `iosApp/` — iOS app shell for the sample.

### Package structure (`shared/src/commonMain`)
```
com.nubasu.spotify.webapi.wrapper
├── api/                   # Domain API classes + ApiResponseExtensions.kt
│   ├── albums/AlbumsApis.kt
│   ├── authorization/     # AuthorizationApis.kt, SpotifyAuthManager.kt, AuthorizationUriLauncher.kt
│   └── <domain>/          # One *Apis.kt per Spotify domain
├── request/               # Request parameter models
│   └── common/            # PagingOptions, Ids, Uris, MarketOptions, IncludeGroup
├── response/              # kotlinx.serialization response models
│   └── common/            # SpotifyApiResponse<T>, SpotifyResponseData (sealed), SpotifyError
└── utils/                 # TokenHolder, PagingHelpers, RateLimitHandling, RetryPolicies, CountryCode
```

### Key design patterns

**API classes** — Each Spotify domain has a `*Apis` class (e.g., `AlbumsApis`) that takes an optional `HttpClient` constructor parameter (default: real CIO client). Methods are `suspend` and return `SpotifyApiResponse<T>`.

**Response type** — `SpotifyApiResponse<T>` wraps `statusCode`, `headers`, and `data: SpotifyResponseData<T>`. `SpotifyResponseData` is a sealed class with `Success<T>` and `Error` variants. The `HttpResponse.toSpotifyApiResponse()` extension in `ApiResponseExtensions.kt` handles the conversion.

**Token management** — `TokenHolder` is a global singleton (`object`) holding the bearer token string used by all API classes. `SpotifyAuthManager` is the high-level auth coordinator; after a successful auth flow it writes the token to `TokenHolder.token` automatically.

**Auth flows** — `SpotifyAuthManager` supports PKCE, Authorization Code, Client Credentials, and Refresh Token. Use `startPkceAuthorizationAndLaunch()` → `completePkceAuthorizationFromRedirectUri()` for the PKCE flow.

**Shared request helpers** — Reuse `PagingOptions`, `Ids`, `Uris`, `CountryCode`, etc. for query/body parameters. Do not add platform-specific code to `commonMain`.

**Utilities** — `PagingHelpers.collectAllItems()` auto-fetches all pages. `RateLimitHandling` reads `Retry-After` headers. `SpotifyRetryExecutor` with `RetryPolicy` implements exponential backoff with jitter.

### Testing conventions
- All non-private functions must have unit tests (TDD policy).
- Tests use `ktor-client-mock` via `ApiTestClientFactory` (returns a `MockEngine`-backed `HttpClient`).
- `ApiStatusCaseAsserts` provides shared assertions for standard HTTP error status codes.
- JSON fixture files live under `commonTest/.../api/fixtures/`.
- Tests run with `kotlinx-coroutines-test` (`runTest`).

### Adding a new endpoint
1. Check the Spotify Web API reference for path, query, body, and response fields.
2. Add/update response model in `response/<domain>/` with `@Serializable`.
3. Add/update request model in `request/<domain>/` if needed, or reuse common types.
4. Add a `suspend` method to the matching `*Apis` class using the existing Ktor builder pattern (bearer auth, `toSpotifyApiResponse()`).
5. Add tests mirroring the pattern in `AlbumsApisTest.kt`.
6. Verify with `./gradlew :shared:compileKotlinJvm` and `./gradlew :shared:jvmTest`.
