package com.nubasu.spotify.webapi.wrapper.request.users

import kotlinx.serialization.Serializable

@Serializable
data class FollowPlaylistRequest(
    val public: Boolean,
)
