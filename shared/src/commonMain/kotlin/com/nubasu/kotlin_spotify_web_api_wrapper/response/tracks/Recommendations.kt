package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

data class Recommendations(
    val seed: List<RecommendationSeedObject>,
    val tracks: List<TrackObject>,
)