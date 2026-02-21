# Paging / Rate Limit / Retry Guide

## Paging

```kotlin
import com.nubasu.spotify.webapi.wrapper.utils.PagingHelpers

val first = tracksApis.getUsersSavedTracks()
val all = PagingHelpers.collectAllItems(
    firstPageResponse = first,
    nextUrlSelector = { it.next },
    itemsSelector = { it.items },
    fetchNextPage = { paging -> tracksApis.getUsersSavedTracks(pagingOptions = paging) }
)
```

## Rate Limit

```kotlin
import com.nubasu.spotify.webapi.wrapper.utils.RateLimitHandling

val retryAfterMillis = RateLimitHandling.retryAfterDelayMillis(response)
```

## Retry

```kotlin
import com.nubasu.spotify.webapi.wrapper.utils.RetryPolicy
import com.nubasu.spotify.webapi.wrapper.utils.SpotifyRetryExecutor

val executor = SpotifyRetryExecutor(
    RetryPolicy(
        maxRetries = 3,
        baseDelayMillis = 500,
        retryStatusCodes = setOf(429, 500, 502, 503, 504),
        respectRetryAfterHeader = true,
    )
)

val response = executor.execute {
    albumsApis.getAlbum("album-id")
}
```

