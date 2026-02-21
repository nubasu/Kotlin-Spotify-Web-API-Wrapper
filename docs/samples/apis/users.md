# Users API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.users.UsersApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.users.FollowType
import com.nubasu.kotlin_spotify_web_api_wrapper.request.users.TopItemType

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

