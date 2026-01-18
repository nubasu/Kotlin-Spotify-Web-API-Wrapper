package com.nubasu.kotlin_spotify_web_api_wrapper

import com.nubasu.kotlin_spotify_web_api_wrapper.api.albums.AlbumsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import kotlinx.coroutines.runBlocking

object ApiTest {
    fun albumApisTest() = runBlocking {
        val albumApis = AlbumsApis()
        println(albumApis.getAlbum("4aawyAB9vmqN3uQ7FjRGTy"))
        println(albumApis.getSeveralAlbums(listOf("4aawyAB9vmqN3uQ7FjRGTy", "4aawyAB9vmqN3uQ7FjRGTy")))
        println(albumApis.getAlbumTracks("4aawyAB9vmqN3uQ7FjRGTy",))
        println(albumApis.getUsersSavedAlbums())
        println(albumApis.saveAlbumsForCurrentUser(Ids(listOf("4aawyAB9vmqN3uQ7FjRGTy"))))
        println(albumApis.checkUsersSavedAlbums(Ids(listOf("4aawyAB9vmqN3uQ7FjRGTy"))))
        println(albumApis.removeUsersSavedAlbums(Ids(listOf("4aawyAB9vmqN3uQ7FjRGTy"))))
        println(albumApis.checkUsersSavedAlbums(Ids(listOf("4aawyAB9vmqN3uQ7FjRGTy"))))
        println(albumApis.getNewReleases())
    }
}