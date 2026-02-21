# Library API Samples

```kotlin
import com.nubasu.kotlin_spotify_web_api_wrapper.api.library.LibraryApis

val api = LibraryApis()

api.getUsersSavedLibrary()
api.saveItemsForCurrentUser(listOf("spotify:track:4uLU6hMCjMI75M1A2tKUQC"))
api.removeUsersSavedItems(listOf("spotify:track:4uLU6hMCjMI75M1A2tKUQC"))
api.checkUsersSavedItems(listOf("spotify:track:4uLU6hMCjMI75M1A2tKUQC"))
```
