# Playlists API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.playlists.PlaylistsApis
import com.nubasu.spotify.webapi.wrapper.request.playlists.AddItemsToPlaylistRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.ChangePlaylistDetailsRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.CreatePlaylistRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.RemovePlaylistItemsRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.TrackUri
import com.nubasu.spotify.webapi.wrapper.request.playlists.UpdatePlaylistItemsRequest

val api = PlaylistsApis()

api.getPlaylist("playlist-id")
api.changePlaylistDetails("playlist-id", ChangePlaylistDetailsRequest(name = "new-name"))
api.getPlaylistItems("playlist-id")
api.updatePlaylistItems("playlist-id", UpdatePlaylistItemsRequest(uris = listOf("spotify:track:...")))
api.addItemsToPlaylist("playlist-id", AddItemsToPlaylistRequest(uris = listOf("spotify:track:...")))
api.removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(items = listOf(TrackUri("spotify:track:..."))))
api.getCurrentUsersPlaylists()
api.getUsersPlaylists("user-id")
api.createPlaylist(CreatePlaylistRequest(name = "My Playlist"))
api.createPlaylist("user-id", CreatePlaylistRequest(name = "My Playlist"))
api.getFeaturedPlaylists()
api.getCategorysPlaylists("party")
api.getPlaylistCoverImage("playlist-id")
api.addCustomPlaylistCoverImage("playlist-id", "base64-jpeg")
```

