# Episodes API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.episodes.EpisodesApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids

val api = EpisodesApis()

api.getEpisode("episode-id")
api.getSeveralEpisodes(listOf("episode-1", "episode-2"))
api.getUsersSavedEpisodes()
api.saveEpisodesForCurrentUser(Ids(listOf("episode-1")))
api.removeUsersSavedEpisodes(Ids(listOf("episode-1")))
api.checkUsersSavedEpisodes(Ids(listOf("episode-1")))
```

