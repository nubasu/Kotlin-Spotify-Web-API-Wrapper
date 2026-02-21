package com.nubasu.spotify.webapi.wrapper.response.users

import kotlinx.serialization.Serializable

@Serializable
data class FollowedArtists(
    val artists: Artists
)
