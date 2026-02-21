# Shows API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.shows.ShowsApis
import com.nubasu.spotify.webapi.wrapper.request.common.Ids

val api = ShowsApis()

api.getShow("show-id")
api.getSeveralShows(listOf("show-1", "show-2"))
api.getShowEpisodes("show-id")
api.getUsersSavedShows()
api.saveShowsForCurrentUser(Ids(listOf("show-1")))
api.removeUsersSavedShows(Ids(listOf("show-1")))
api.checkUsersSavedShows(Ids(listOf("show-1")))
```

