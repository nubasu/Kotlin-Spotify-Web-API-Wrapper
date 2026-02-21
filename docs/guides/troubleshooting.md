# Troubleshooting

## `Spotify token is missing`

`TokenHolder.token` or `TokenHolder.tokenProvider` is not configured.

```kotlin
TokenHolder.token = "YOUR_ACCESS_TOKEN"
```

Or run an authorization flow and acquire a token.

## `401 Unauthorized`

- Access token has expired
- Required scope is missing
- Token type mismatch (for example, calling user APIs with Client Credentials)

Try refresh with `SpotifyAuthManager.getValidAccessToken(autoRefresh = true)`.

## `403 Forbidden`

Access is denied due to user permission, market restrictions, or account state.
Check the required scopes for the target API.

## `429 Too Many Requests`

Respect the `Retry-After` header for backoff timing.
Use `SpotifyRetryExecutor` when possible.

## PKCE `State mismatch`

The `state` issued by `startPkceAuthorization()` does not match the callback `state`.
Make sure authorization start and callback completion are handled in the same session.