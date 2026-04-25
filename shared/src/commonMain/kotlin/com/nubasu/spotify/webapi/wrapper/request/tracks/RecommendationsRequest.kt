package com.nubasu.spotify.webapi.wrapper.request.tracks

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

internal fun RecommendationTunableAttributes.toQueryParams(): List<Pair<String, String>> = buildList {
    minAcousticness?.let { add("min_acousticness" to it.toString()) }
    maxAcousticness?.let { add("max_acousticness" to it.toString()) }
    targetAcousticness?.let { add("target_acousticness" to it.toString()) }
    minDanceability?.let { add("min_danceability" to it.toString()) }
    maxDanceability?.let { add("max_danceability" to it.toString()) }
    targetDanceability?.let { add("target_danceability" to it.toString()) }
    minDurationMs?.let { add("min_duration_ms" to it.toString()) }
    maxDurationMs?.let { add("max_duration_ms" to it.toString()) }
    targetDurationMs?.let { add("target_duration_ms" to it.toString()) }
    minEnergy?.let { add("min_energy" to it.toString()) }
    maxEnergy?.let { add("max_energy" to it.toString()) }
    targetEnergy?.let { add("target_energy" to it.toString()) }
    minInstrumentalness?.let { add("min_instrumentalness" to it.toString()) }
    maxInstrumentalness?.let { add("max_instrumentalness" to it.toString()) }
    targetInstrumentalness?.let { add("target_instrumentalness" to it.toString()) }
    minKey?.let { add("min_key" to it.toString()) }
    maxKey?.let { add("max_key" to it.toString()) }
    targetKey?.let { add("target_key" to it.toString()) }
    minLiveness?.let { add("min_liveness" to it.toString()) }
    maxLiveness?.let { add("max_liveness" to it.toString()) }
    targetLiveness?.let { add("target_liveness" to it.toString()) }
    minLoudness?.let { add("min_loudness" to it.toString()) }
    maxLoudness?.let { add("max_loudness" to it.toString()) }
    targetLoudness?.let { add("target_loudness" to it.toString()) }
    minMode?.let { add("min_mode" to it.toString()) }
    maxMode?.let { add("max_mode" to it.toString()) }
    targetMode?.let { add("target_mode" to it.toString()) }
    minPopularity?.let { add("min_popularity" to it.toString()) }
    maxPopularity?.let { add("max_popularity" to it.toString()) }
    targetPopularity?.let { add("target_popularity" to it.toString()) }
    minSpeechiness?.let { add("min_speechiness" to it.toString()) }
    maxSpeechiness?.let { add("max_speechiness" to it.toString()) }
    targetSpeechiness?.let { add("target_speechiness" to it.toString()) }
    minTempo?.let { add("min_tempo" to it.toString()) }
    maxTempo?.let { add("max_tempo" to it.toString()) }
    targetTempo?.let { add("target_tempo" to it.toString()) }
    minTimeSignature?.let { add("min_time_signature" to it.toString()) }
    maxTimeSignature?.let { add("max_time_signature" to it.toString()) }
    targetTimeSignature?.let { add("target_time_signature" to it.toString()) }
    minValence?.let { add("min_valence" to it.toString()) }
    maxValence?.let { add("max_valence" to it.toString()) }
    targetValence?.let { add("target_valence" to it.toString()) }
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
