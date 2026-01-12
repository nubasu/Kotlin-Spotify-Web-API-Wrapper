package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Bars
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Beats
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Meta
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Sections
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Segments
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.Tatums
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis.TrackAnalysis

data class TracksAudioAnalysis(
    val meta: Meta,
    val track: TrackAnalysis,
    val bars: Bars,
    val beats: Beats,
    val sections: Sections,
    val segments: Segments,
    val tatums: Tatums,
)
