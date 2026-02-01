package com.nubasu.kotlin_spotify_web_api_wrapper

import com.nubasu.kotlin_spotify_web_api_wrapper.api.albums.AlbumsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.artists.ArtistsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.audiobooks.AudiobooksApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.categories.CategoriesApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.chapters.ChaptersApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.episodes.EpisodesApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.genres.GenresApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.markets.MarketsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.player.PlayerApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.DeviceIds
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.RepeatMode
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.State
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

    fun audiobookApisTest() = runBlocking {
        val api = AudiobooksApis()
        println(api.getAnAudiobook("7iHfbu1YPACw6oZPAFJtqe"))
        println(api.getSeveralAudiobooks(listOf("18yVqkdbdRvS24c0Ilj2ci","1HGw3J3NxZO1TP1BTtVhpZ","7iHfbu1YPACw6oZPAFJtqe")))
        println(api.getAudiobookChapters("7iHfbu1YPACw6oZPAFJtqe"))
        println(api.getUsersSavedAudiobooks())
        println(api.saveAudiobooksForCurrentUser(Ids(listOf("7iHfbu1YPACw6oZPAFJtqe"))))
        println(api.checkUsersSavedAudiobooks(Ids(listOf("18yVqkdbdRvS24c0Ilj2ci","1HGw3J3NxZO1TP1BTtVhpZ","7iHfbu1YPACw6oZPAFJtqe"))))
        println(api.removeUsersSavedAudiobooks(Ids(listOf("7iHfbu1YPACw6oZPAFJtqe"))))
        println(api.checkUsersSavedAudiobooks(Ids(listOf("18yVqkdbdRvS24c0Ilj2ci","1HGw3J3NxZO1TP1BTtVhpZ","7iHfbu1YPACw6oZPAFJtqe"))))
    }

    fun categoryApisTest() = runBlocking {
        val api = CategoriesApis()
        println(api.getSeveralBrowseCategories())
        println(api.getSingleBrowseCategory("dinner"))
    }

    fun chaptersApisTest() = runBlocking {
        val api = ChaptersApis()
        println(api.getAChapter("0D5wENdkdwbqlrHoaJ9g29"))
        println(api.getSeveralChapters(listOf("0IsXVP0JmcB2adSE338GkK","3ZXb8FKZGU0EHALYX6uCzU","0D5wENdkdwbqlrHoaJ9g29")))
    }

    fun episodeApisTest() = runBlocking {
        val api = EpisodesApis()
        println(api.getEpisode("512ojhOuo1ktJprKbVcKyQ"))
        println(api.getSeveralEpisodes(listOf("77o6BIVlYM3msb4MMIL1jH", "0Q86acNRm6V9GYx55SXKwf")))
        println(api.getUsersSavedEpisodes())
        println(api.saveEpisodesForCurrentUser(Ids(listOf("77o6BIVlYM3msb4MMIL1jH", "0Q86acNRm6V9GYx55SXKwf"))))
        println(api.checkUsersSavedEpisodes(Ids(listOf("77o6BIVlYM3msb4MMIL1jH"))))
        println(api.removeUsersSavedEpisodes(Ids(listOf("77o6BIVlYM3msb4MMIL1jH", "0Q86acNRm6V9GYx55SXKwf"))))
        println(api.checkUsersSavedEpisodes(Ids(listOf("77o6BIVlYM3msb4MMIL1jH", "0Q86acNRm6V9GYx55SXKwf"))))
    }

    fun genresApisTest() = runBlocking {
        val api = GenresApis()
        println(api.getAvailableGenreSeeds())
    }

    fun marketApisTest() = runBlocking {
        val api = MarketsApis()
        println(api.getAvailableMarkets())
    }

    fun playerApisTest() = runBlocking {
        val api = PlayerApis()
        val devices = api.getAvailableDevices()
        println("getAvailableDevices")
        println(devices)
        println("getPlaybackState")
        println(api.getPlaybackState())
        println("transferPlayback")
        println(api.transferPlayback(DeviceIds(listOf(devices.devices.first().id.toString()))))
        println("getCurrentlyPlayingTrack")
        println(api.getCurrentlyPlayingTrack())
        println("startResumePlayback")
        println(api.startResumePlayback())
        println("pausePlayback")
        println(api.pausePlayback())
        println("skipToNext")
        println(api.skipToNext())
        println("skipToPrevious")
        println(api.skipToPrevious())
        println("seekToPosition")
        println(api.seekToPosition(positionMs = 25000))
        println("setRepeatMode")
        println(api.setRepeatMode(state = State(RepeatMode.OFF)))
        println("setPlaybackVolume")
        println(api.setPlaybackVolume(0))
        println("togglePlaybackShuffle")
        println(api.togglePlaybackShuffle(false))
        println("getRecentlyPlayedTracks")
        println(api.getRecentlyPlayedTracks())
        println("getTheUsersQueue")
        println(api.getTheUsersQueue())
        println("addItemToPlaybackQueue")
        println(api.addItemToPlaybackQueue("spotify:track:4iV5W9uYEdYUVa79Axb7Rh"))
        println("")
    }
}