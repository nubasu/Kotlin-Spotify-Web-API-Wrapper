# Audiobooks API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.audiobooks.AudiobooksApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids

val api = AudiobooksApis()

api.getAnAudiobook("audiobook-id")
api.getSeveralAudiobooks(listOf("audiobook-1", "audiobook-2"))
api.getAudiobookChapters("audiobook-id")
api.getUsersSavedAudiobooks()
api.saveAudiobooksForCurrentUser(Ids(listOf("audiobook-1")))
api.removeUsersSavedAudiobooks(Ids(listOf("audiobook-1")))
api.checkUsersSavedAudiobooks(Ids(listOf("audiobook-1")))
```

