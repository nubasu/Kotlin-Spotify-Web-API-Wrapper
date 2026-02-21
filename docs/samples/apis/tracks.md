# Tracks API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.tracks.TracksApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.RecommendationSeeds
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.RecommendationTunableAttributes
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.SaveTracksForCurrentUserRequest

val api = TracksApis()

api.getTrack("track-id")
api.getSeveralTracks(listOf("track-1", "track-2"))
api.getUsersSavedTracks()
api.saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("track-1")))
api.removeUsersSavedTracks(Ids(listOf("track-1")))
api.checkUsersSavedTracks(Ids(listOf("track-1")))
api.getTracksAudioFeatures("track-id")
api.getSeveralTracksAudioFeatures(listOf("track-1", "track-2"))
api.getTracksAudioAnalysis("track-id")
api.getRecommendations(
    seeds = RecommendationSeeds(genres = listOf("pop")),
    tunable = RecommendationTunableAttributes(targetEnergy = 0.7),
)
```

