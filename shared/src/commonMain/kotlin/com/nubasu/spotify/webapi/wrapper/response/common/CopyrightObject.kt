package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class CopyrightObject(
    val text: String? = null,
    val type: String? = null,
)
