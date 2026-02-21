package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Bars
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Beats
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Meta
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Sections
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Segments
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Tatums
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.TrackAnalysis
import kotlinx.serialization.Serializable

@Serializable
data class TracksAudioAnalysis(
    val meta: Meta = Meta(),
    val track: TrackAnalysis = TrackAnalysis(),
    val bars: List<Bars> = emptyList(),
    val beats: List<Beats> = emptyList(),
    val sections: List<Sections> = emptyList(),
    val segments: List<Segments> = emptyList(),
    val tatums: List<Tatums> = emptyList(),
)
