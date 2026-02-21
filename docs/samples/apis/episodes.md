# Episodes API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.episodes.EpisodesApis
import com.nubasu.spotify.webapi.wrapper.request.common.Ids

val api = EpisodesApis()

api.getEpisode("episode-id")
api.getSeveralEpisodes(listOf("episode-1", "episode-2"))
api.getUsersSavedEpisodes()
api.saveEpisodesForCurrentUser(Ids(listOf("episode-1")))
api.removeUsersSavedEpisodes(Ids(listOf("episode-1")))
api.checkUsersSavedEpisodes(Ids(listOf("episode-1")))
```

