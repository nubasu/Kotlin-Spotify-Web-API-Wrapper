package com.nubasu.spotify.webapi.wrapper.response.tracks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TracksAudioFeatures(
    @SerialName("audio_features")
    val audioFeatures: List<AudioFeaturesObject>,
)
