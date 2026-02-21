# Search API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.search.SearchApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.search.SearchType

val api = SearchApis()
api.searchForItem("Muse", setOf(SearchType.ARTIST, SearchType.TRACK))
```

