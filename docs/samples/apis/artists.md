# Artists API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.artists.ArtistsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.IncludeGroup

val api = ArtistsApis()

api.getArtist("artist-id")
api.getSeveralArtists(listOf("artist-1", "artist-2"))
api.getArtistsAlbums("artist-id", includeGroups = listOf(IncludeGroup.Album))
api.getArtistsTopTracks("artist-id")
api.getArtistsRelatedArtists("artist-id")
```

