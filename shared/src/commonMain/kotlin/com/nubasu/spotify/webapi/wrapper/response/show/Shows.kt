package com.nubasu.spotify.webapi.wrapper.response.show

import kotlinx.serialization.Serializable

@Serializable
data class Shows(
    val shows: List<SimplifiedShowObject>,
)
