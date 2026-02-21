package com.nubasu.kotlin_spotify_web_api_wrapper.request.common

import kotlinx.serialization.Serializable

@Serializable
data class Uris(val ids: List<String>)
