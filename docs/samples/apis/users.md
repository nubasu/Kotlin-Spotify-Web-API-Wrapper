# Users API Samples

```kotlin
import com.nubasu.spotify.webapi.wrapper.api.users.UsersApis
import com.nubasu.spotify.webapi.wrapper.request.users.FollowType
import com.nubasu.spotify.webapi.wrapper.request.users.TopItemType

val api = UsersApis()

api.getCurrentUsersProfile()
api.getUsersTopItems(TopItemType.ARTISTS)
api.getUsersProfile("user-id")
api.followPlaylist("playlist-id", public = true)
api.unfollowPlaylist("playlist-id")
api.getFollowedArtists(type = FollowType.ARTIST)
api.followArtistsOrUsers(FollowType.ARTIST, listOf("artist-id"))
api.unfollowArtistsOrUsers(FollowType.ARTIST, listOf("artist-id"))
api.checkIfUserFollowsArtistsOrUsers(FollowType.ARTIST, listOf("artist-id"))
api.checkIfCurrentUserFollowsPlaylist("playlist-id", listOf("user-id"))
```

