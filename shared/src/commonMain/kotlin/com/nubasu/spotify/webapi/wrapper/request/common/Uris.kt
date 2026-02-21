package com.nubasu.spotify.webapi.wrapper.request.common

import kotlinx.serialization.Serializable

@Serializable
data class Uris(val ids: List<String>)
