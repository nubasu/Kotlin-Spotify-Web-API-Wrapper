package com.nubasu.kotlin_spotify_web_api_wrapper.api.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.RecommendationSeeds
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.RecommendationTunableAttributes
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.SaveTracksForCurrentUserRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.OneTrackAudioFeatures
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Recommendations
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Track
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TracksAudioAnalysis
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TracksAudioFeatures
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Tracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.UsersSavedTrack
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class TracksApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }
) {

    suspend fun getTrack(id: String, market: CountryCode? = null) : SpotifyApiResponse<Track> {
        val endpoint = "https://api.spotify.com/v1/tracks"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(id)
                market?.let { parameters.append("market", it.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/tracks as deprecated.",
    )
    suspend fun getSeveralTracks(ids: List<String>, market: CountryCode? = null) : SpotifyApiResponse<Tracks> {
        val endpoint = "https://api.spotify.com/v1/tracks"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.joinToString(","))
                market?.let { parameters.append("market", it.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyApiResponse()
    }

    suspend fun getUsersSavedTracks(market: CountryCode? = null, pagingOptions: PagingOptions = PagingOptions()) : SpotifyApiResponse<UsersSavedTrack> {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.get {
            url {
                takeFrom(endpoint)
                market?.let { parameters.append("market", it.code) }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks PUT /v1/me/tracks as deprecated.",
    )
    suspend fun saveTracksForCurrentUser(body: SaveTracksForCurrentUserRequest) : SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.put(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }
return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks DELETE /v1/me/tracks as deprecated.",
    )
    suspend fun removeUsersSavedTracks(ids: Ids) : SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.delete {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyBooleanApiResponse()
    }

    suspend fun checkUsersSavedTracks(ids: Ids) : SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/tracks/contains"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/audio-features/{id} as deprecated.",
    )
    suspend fun getTracksAudioFeatures(id: String): SpotifyApiResponse<OneTrackAudioFeatures> {
        val endpoint = "https://api.spotify.com/v1/audio-features"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(id)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/audio-features as deprecated.",
    )
    suspend fun getSeveralTracksAudioFeatures(ids: List<String>): SpotifyApiResponse<TracksAudioFeatures> {
        val endpoint = "https://api.spotify.com/v1/audio-features"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/audio-analysis/{id} as deprecated.",
    )
    suspend fun getTracksAudioAnalysis(id: String): SpotifyApiResponse<TracksAudioAnalysis> {
        val endpoint = "https://api.spotify.com/v1/audio-analysis"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(id)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    suspend fun getRecommendations(
        seeds: RecommendationSeeds,
        market: CountryCode? = null,
        limit: Int? = null,
        tunable: RecommendationTunableAttributes = RecommendationTunableAttributes(),
    ): SpotifyApiResponse<Recommendations> {
        val endpoint = "https://api.spotify.com/v1/recommendations"
        val response = client.get {
            url {
                takeFrom(endpoint)
                market?.let { parameters.append("market", it.code) }
                limit?.let { parameters.append("limit", it.toString()) }
                if (seeds.artists.isNotEmpty()) {
                    parameters.append("seed_artists", seeds.artists.joinToString(","))
                }
                if (seeds.genres.isNotEmpty()) {
                    parameters.append("seed_genres", seeds.genres.joinToString(","))
                }
                if (seeds.tracks.isNotEmpty()) {
                    parameters.append("seed_tracks", seeds.tracks.joinToString(","))
                }
                tunable.appendToQuery(parameters::append)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    private fun RecommendationTunableAttributes.appendToQuery(append: (String, String) -> Unit) {
        minAcousticness?.let { append("min_acousticness", it.toString()) }
        maxAcousticness?.let { append("max_acousticness", it.toString()) }
        targetAcousticness?.let { append("target_acousticness", it.toString()) }
        minDanceability?.let { append("min_danceability", it.toString()) }
        maxDanceability?.let { append("max_danceability", it.toString()) }
        targetDanceability?.let { append("target_danceability", it.toString()) }
        minDurationMs?.let { append("min_duration_ms", it.toString()) }
        maxDurationMs?.let { append("max_duration_ms", it.toString()) }
        targetDurationMs?.let { append("target_duration_ms", it.toString()) }
        minEnergy?.let { append("min_energy", it.toString()) }
        maxEnergy?.let { append("max_energy", it.toString()) }
        targetEnergy?.let { append("target_energy", it.toString()) }
        minInstrumentalness?.let { append("min_instrumentalness", it.toString()) }
        maxInstrumentalness?.let { append("max_instrumentalness", it.toString()) }
        targetInstrumentalness?.let { append("target_instrumentalness", it.toString()) }
        minKey?.let { append("min_key", it.toString()) }
        maxKey?.let { append("max_key", it.toString()) }
        targetKey?.let { append("target_key", it.toString()) }
        minLiveness?.let { append("min_liveness", it.toString()) }
        maxLiveness?.let { append("max_liveness", it.toString()) }
        targetLiveness?.let { append("target_liveness", it.toString()) }
        minLoudness?.let { append("min_loudness", it.toString()) }
        maxLoudness?.let { append("max_loudness", it.toString()) }
        targetLoudness?.let { append("target_loudness", it.toString()) }
        minMode?.let { append("min_mode", it.toString()) }
        maxMode?.let { append("max_mode", it.toString()) }
        targetMode?.let { append("target_mode", it.toString()) }
        minPopularity?.let { append("min_popularity", it.toString()) }
        maxPopularity?.let { append("max_popularity", it.toString()) }
        targetPopularity?.let { append("target_popularity", it.toString()) }
        minSpeechiness?.let { append("min_speechiness", it.toString()) }
        maxSpeechiness?.let { append("max_speechiness", it.toString()) }
        targetSpeechiness?.let { append("target_speechiness", it.toString()) }
        minTempo?.let { append("min_tempo", it.toString()) }
        maxTempo?.let { append("max_tempo", it.toString()) }
        targetTempo?.let { append("target_tempo", it.toString()) }
        minTimeSignature?.let { append("min_time_signature", it.toString()) }
        maxTimeSignature?.let { append("max_time_signature", it.toString()) }
        targetTimeSignature?.let { append("target_time_signature", it.toString()) }
        minValence?.let { append("min_valence", it.toString()) }
        maxValence?.let { append("max_valence", it.toString()) }
        targetValence?.let { append("target_valence", it.toString()) }
    }
}
