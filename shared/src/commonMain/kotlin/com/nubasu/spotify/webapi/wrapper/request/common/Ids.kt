package com.nubasu.spotify.webapi.wrapper.request.common

import kotlinx.serialization.Serializable

@Serializable
data class Ids(val ids: List<String>)