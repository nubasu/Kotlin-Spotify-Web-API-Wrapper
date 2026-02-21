package com.nubasu.kotlin_spotify_web_api_wrapper.response.show

import kotlinx.serialization.Serializable

@Serializable
data class Shows(
    val shows: List<SimplifiedShowObject>,
)
