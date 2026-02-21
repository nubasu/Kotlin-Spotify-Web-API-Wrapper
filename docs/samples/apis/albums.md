# Albums API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.albums.AlbumsApis
import com.nubasu.spotify.webapi.wrapper.request.common.Ids

val api = AlbumsApis()

api.getAlbum("album-id")
api.getSeveralAlbums(listOf("album-1", "album-2"))
api.getAlbumTracks("album-id")
api.getUsersSavedAlbums()
api.saveAlbumsForCurrentUser(Ids(listOf("album-1")))
api.removeUsersSavedAlbums(Ids(listOf("album-1")))
api.checkUsersSavedAlbums(Ids(listOf("album-1")))
api.getNewReleases()
```

