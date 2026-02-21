package com.nubasu.kotlin_spotify_web_api_wrapper.request.users

import kotlinx.serialization.Serializable

@Serializable
data class FollowPlaylistRequest(
    val public: Boolean,
)
