# Search API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.search.SearchApis
import com.nubasu.spotify.webapi.wrapper.request.search.SearchType

val api = SearchApis()
api.searchForItem("Muse", setOf(SearchType.ARTIST, SearchType.TRACK))
```

