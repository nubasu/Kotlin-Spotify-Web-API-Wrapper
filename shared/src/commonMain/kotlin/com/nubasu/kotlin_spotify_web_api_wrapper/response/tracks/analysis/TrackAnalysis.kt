package com.nubasu.spotify.webapi.wrapper.response.tracks.analysis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackAnalysis(
    @SerialName("num_samples")
    val numSamples: Int? = null,
    val duration: Double? = null,
    @SerialName("sample_md5")
    val sampleMd5: String? = null,
    @SerialName("offset_seconds")
    val offsetSeconds: Int? = null,
    @SerialName("window_seconds")
    val windowSeconds: Int? = null,
    @SerialName("analysis_sample_rate")
    val analysisSampleRate: Int? = null,
    @SerialName("analysis_channels")
    val analysisChannels: Int? = null,
    @SerialName("end_of_fade_in")
    val endOfFadeIn: Double? = null,
    @SerialName("start_of_fade_out")
    val startOfFadeOut: Double? = null,
    val loudness: Double? = null,
    val tempo: Double? = null,
    @SerialName("tempo_confidence")
    val tempoConfidence: Double? = null,
    @SerialName("time_signature")
    val timeSignature: Int? = null,
    @SerialName("time_signature_confidence")
    val timeSignatureConfidence: Double? = null,
    val key: Int? = null,
    @SerialName("key_confidence")
    val keyConfidence: Double? = null,
    val mode: Int? = null,
    @SerialName("mode_confidence")
    val modeConfidence: Double? = null,
    val codestring: String? = null,
    @SerialName("code_version")
    val codeVersion: Double? = null,
    val echoprintstring: String? = null,
    @SerialName("echoprint_version")
    val echoprintVersion: Double? = null,
    val synchstring: String? = null,
    @SerialName("synch_version")
    val synchVersion: Double? = null,
    val rhythmstring: String? = null,
    @SerialName("rhythm_version")
    val rhythmVersion: Double? = null,
)
