package com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks

data class RecommendationSeeds(
    val artists: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val tracks: List<String> = emptyList(),
) {
    init {
        val total = artists.size + genres.size + tracks.size
        require(total in 1..5) {
            "Spotify recommendations requires 1 to 5 seed values in total."
        }
    }
}

data class RecommendationTunableAttributes(
    val minAcousticness: Double? = null,
    val maxAcousticness: Double? = null,
    val targetAcousticness: Double? = null,
    val minDanceability: Double? = null,
    val maxDanceability: Double? = null,
    val targetDanceability: Double? = null,
    val minDurationMs: Int? = null,
    val maxDurationMs: Int? = null,
    val targetDurationMs: Int? = null,
    val minEnergy: Double? = null,
    val maxEnergy: Double? = null,
    val targetEnergy: Double? = null,
    val minInstrumentalness: Double? = null,
    val maxInstrumentalness: Double? = null,
    val targetInstrumentalness: Double? = null,
    val minKey: Int? = null,
    val maxKey: Int? = null,
    val targetKey: Int? = null,
    val minLiveness: Double? = null,
    val maxLiveness: Double? = null,
    val targetLiveness: Double? = null,
    val minLoudness: Double? = null,
    val maxLoudness: Double? = null,
    val targetLoudness: Double? = null,
    val minMode: Int? = null,
    val maxMode: Int? = null,
    val targetMode: Int? = null,
    val minPopularity: Int? = null,
    val maxPopularity: Int? = null,
    val targetPopularity: Int? = null,
    val minSpeechiness: Double? = null,
    val maxSpeechiness: Double? = null,
    val targetSpeechiness: Double? = null,
    val minTempo: Double? = null,
    val maxTempo: Double? = null,
    val targetTempo: Double? = null,
    val minTimeSignature: Int? = null,
    val maxTimeSignature: Int? = null,
    val targetTimeSignature: Int? = null,
    val minValence: Double? = null,
    val maxValence: Double? = null,
    val targetValence: Double? = null,
)
