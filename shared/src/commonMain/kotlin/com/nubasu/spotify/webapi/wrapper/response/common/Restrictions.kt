package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class Restrictions(
    val reason: String,
)
