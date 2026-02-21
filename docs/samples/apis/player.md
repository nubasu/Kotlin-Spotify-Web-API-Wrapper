# Player API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.player.PlayerApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.DeviceIds
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.Offset
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.RepeatMode
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.StartResumePlaybackRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.State
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.TransferPlaybackRequest

val api = PlayerApis()

api.getPlaybackState()
api.transferPlayback(TransferPlaybackRequest(deviceIds = DeviceIds(listOf("device-id"))))
api.getAvailableDevices()
api.getCurrentlyPlayingTrack()
api.startResumePlayback(StartResumePlaybackRequest(offset = Offset(uri = "spotify:track:...")))
api.pausePlayback()
api.skipToNext()
api.skipToPrevious()
api.seekToPosition(30_000)
api.setRepeatMode(RepeatMode.CONTEXT)
api.setPlaybackVolume(50)
api.togglePlaybackShuffle(State(true))
api.getRecentlyPlayedTracks(limit = 20)
api.getTheUsersQueue()
api.addItemToPlaybackQueue("spotify:track:...")
```

