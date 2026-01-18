package com.nubasu.kotlin_spotify_web_api_wrapper

import com.nubasu.kotlin_spotify_web_api_wrapper.api.albums.AlbumsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.artists.ArtistsApis
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

    fun artistApisTest() = runBlocking {
        val artistApis = ArtistsApis()
        println(artistApis.getArtist("0TnOYISbd1XYRBk9myaseg"))
        println(artistApis.getSeveralArtists(listOf("2CIMQHirSU0MQqyYHq0eOx","57dN52uHvrHOxijzpIgu3E","1vCWHaC5f2uS3yhpwWbIA6")))
        println(artistApis.getArtistsAlbums("0TnOYISbd1XYRBk9myaseg"))
        println(artistApis.getArtistsTopTracks("0TnOYISbd1XYRBk9myaseg"))
        println(artistApis.getArtistsRelatedArtists("0TnOYISbd1XYRBk9myaseg"))
    }
}