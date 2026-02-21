# Response / Error Handling

このライブラリの返り値は `SpotifyApiResponse<T>` です。

```kotlin
data class SpotifyApiResponse<T>(
    val statusCode: Int,
    val data: SpotifyResponseData<T>,
    val headers: Map<String, String> = emptyMap(),
)
```

`data` は常に non-null で、`Success` か `Error` のどちらかです。

## 基本パターン

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData

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

## 429 (Rate Limit) の待機時間

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.RateLimitHandling

val retryAfterMillis = RateLimitHandling.retryAfterDelayMillis(response)
if (response.statusCode == 429 && retryAfterMillis != null) {
    println("retryAfterMillis=$retryAfterMillis")
}
```

## ヘッダー取得

```kotlin
val retryAfterRaw = response.header("Retry-After")
```
