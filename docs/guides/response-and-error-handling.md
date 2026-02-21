# Response / Error Handling

This library returns `SpotifyApiResponse<T>`.

```kotlin
data class SpotifyApiResponse<T>(
    val statusCode: Int,
    val data: SpotifyResponseData<T>,
    val headers: Map<String, String> = emptyMap(),
)
```

`data` is always non-null and is either `Success` or `Error`.

## Basic Pattern

```kotlin
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData

suspend fun handleResponse() {
    val response = albumsApis.getAlbum("4aawyAB9vmqN3uQ7FjRGTy")

    when (val data = response.data) {
        is SpotifyResponseData.Success -> {
            println("status=${response.statusCode}")
            println("name=${data.value.name}")
        }
        is SpotifyResponseData.Error -> {
            println("status=${response.statusCode}")
            println("spotifyStatus=${data.value.error.status}")
            println("message=${data.value.error.message}")
        }
    }
}
```

## 429 (Rate Limit) Wait Time

```kotlin
import com.nubasu.spotify.webapi.wrapper.utils.RateLimitHandling

val retryAfterMillis = RateLimitHandling.retryAfterDelayMillis(response)
if (response.statusCode == 429 && retryAfterMillis != null) {
    println("retryAfterMillis=$retryAfterMillis")
}
```

## Read Headers

```kotlin
val retryAfterRaw = response.header("Retry-After")
```