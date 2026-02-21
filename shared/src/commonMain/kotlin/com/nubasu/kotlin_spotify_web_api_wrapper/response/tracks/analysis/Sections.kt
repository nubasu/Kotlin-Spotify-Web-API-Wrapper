package com.nubasu.spotify.webapi.wrapper.response.tracks.analysis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sections(
    val start: Double? = null,
    val duration: Double? = null,
    val confidence: Double? = null,
    val loudness: Double? = null,
    val tempo: Double? = null,
    @SerialName("tempo_confidence")
    val tempoConfidence: Double? = null,
    val key: Int? = null,
    @SerialName("key_confidence")
    val keyConfidence: Double? = null,
    val mode: Int? = null,
    @SerialName("mode_confidence")
    val modeConfidence: Double? = null,
    @SerialName("time_signature")
    val timeSignature: Int? = null,
    @SerialName("time_signature_confidence")
    val timeSignatureConfidence: Double? = null,
)
