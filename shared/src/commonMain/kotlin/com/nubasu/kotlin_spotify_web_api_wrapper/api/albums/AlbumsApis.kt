package com.nubasu.kotlin_spotify_web_api_wrapper.api.albums

import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.Album
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

class AlbumsApis {
    private val client = HttpClient(CIO)
    suspend fun getAlbum() {}

    suspend fun getSeveralAlbums() {}

    suspend fun getAlbumTracks() {}

    suspend fun getUsersSavedAlbums() {}

    suspend fun saveAlbumsForCurrentUser() {}

    suspend fun removeUsersSavedAlbums() {}

    suspend fun checkUsersSavedAlbums() {}

    suspend fun getNewReleases() {}
}
