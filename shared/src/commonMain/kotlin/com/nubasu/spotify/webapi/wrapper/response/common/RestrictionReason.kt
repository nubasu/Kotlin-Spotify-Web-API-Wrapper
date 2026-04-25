package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Reason why a Spotify resource is restricted. */
@Serializable
enum class RestrictionReason {
    @SerialName("market")
    MARKET,

    @SerialName("product")
    PRODUCT,

    @SerialName("explicit")
    EXPLICIT,
}
