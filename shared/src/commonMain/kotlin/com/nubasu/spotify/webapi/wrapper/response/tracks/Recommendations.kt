package com.nubasu.spotify.webapi.wrapper.response.tracks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recommendations(
    @SerialName("seeds")
    val seeds: List<RecommendationSeedObject> = emptyList(),
    val tracks: List<TrackObject> = emptyList(),
) {
    @Deprecated("Use seeds", ReplaceWith("seeds"))
    val seed: List<RecommendationSeedObject>
        get() = seeds
}
