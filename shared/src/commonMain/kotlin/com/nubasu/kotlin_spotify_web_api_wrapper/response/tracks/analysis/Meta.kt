package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("analyzer_version")
    val analyzerVersion: String? = null,
    val platform: String? = null,
    @SerialName("detailed_status")
    val detailedStatus: String? = null,
    @SerialName("status_code")
    val statusCode: Int? = null,
    val timestamp: Long? = null,
    @SerialName("analysis_time")
    val analysisTime: Double? = null,
    @SerialName("input_process")
    val inputProcess: String? = null,
)
