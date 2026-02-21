package com.nubasu.spotify.webapi.wrapper.response.albums

import kotlinx.serialization.Serializable

@Serializable
data class NewRelease(
    val albums: NewReleaseAlbums
)
