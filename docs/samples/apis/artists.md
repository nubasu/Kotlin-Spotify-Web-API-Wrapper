# Artists API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.artists.ArtistsApis
import com.nubasu.spotify.webapi.wrapper.request.common.IncludeGroup

val api = ArtistsApis()

api.getArtist("artist-id")
api.getSeveralArtists(listOf("artist-1", "artist-2"))
api.getArtistsAlbums("artist-id", includeGroups = listOf(IncludeGroup.Album))
api.getArtistsTopTracks("artist-id")
api.getArtistsRelatedArtists("artist-id")
```

