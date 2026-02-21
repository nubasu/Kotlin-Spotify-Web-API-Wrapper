package com.nubasu.spotify.webapi.wrapper.api.fixtures

internal object SpotifyApiFixtures {
    const val ARTIST_MIN = """{}"""

    const val BROWSE_CATEGORIES_MIN = """
        {
          "categories": {
            "href": "https://api.spotify.com/v1/browse/categories",
            "limit": 20,
            "offset": 0,
            "total": 1,
            "items": [
              {
                "href": "https://api.spotify.com/v1/browse/categories/toplists",
                "icons": [
                  {"url": "https://i.scdn.co/image/toplists", "height": 275, "width": 275}
                ],
                "id": "toplists",
                "name": "Top Lists"
              }
            ]
          }
        }
    """

    const val CHAPTERS_MIN = """{"chapters":[]}"""
    const val GENRES_MIN = """{"genres":["pop"]}"""
    const val MARKETS_MIN = """{"markets":["US"]}"""
    const val SEARCH_MIN = """{}"""
    const val LIBRARY_MIN = """
        {
          "href": "https://api.spotify.com/v1/me/library",
          "limit": 20,
          "offset": 0,
          "total": 1,
          "items": [
            {
              "added_at": "2024-01-01T00:00:00Z",
              "item": {
                "id": "t1",
                "name": "Track 1",
                "type": "track"
              }
            }
          ]
        }
    """
    const val TRACK_AUDIO_FEATURES_ONE_MIN = """
        {
          "acousticness": 0.1,
          "analysis_url": "https://api.spotify.com/v1/audio-analysis/t1",
          "danceability": 0.5,
          "duration_ms": 200000,
          "energy": 0.7,
          "id": "t1",
          "instrumentalness": 0.0,
          "key": 5,
          "liveness": 0.1,
          "loudness": -6.1,
          "mode": 1,
          "speechiness": 0.04,
          "tempo": 120.0,
          "time_signature": 4,
          "track_href": "https://api.spotify.com/v1/tracks/t1",
          "type": "audio_features",
          "uri": "spotify:track:t1",
          "valence": 0.8
        }
    """
    const val TRACK_AUDIO_FEATURES_SEVERAL_MIN = """
        {
          "audio_features": [
            {
              "acousticness": 0.1,
              "analysis_url": "https://api.spotify.com/v1/audio-analysis/t1",
              "danceability": 0.5,
              "duration_ms": 200000,
              "energy": 0.7,
              "id": "t1",
              "instrumentalness": 0.0,
              "key": 5,
              "liveness": 0.1,
              "loudness": -6.1,
              "mode": 1,
              "speechiness": 0.04,
              "tempo": 120.0,
              "time_signature": 4,
              "track_href": "https://api.spotify.com/v1/tracks/t1",
              "type": "audio_features",
              "uri": "spotify:track:t1",
              "valence": 0.8
            }
          ]
        }
    """
    const val TRACK_AUDIO_ANALYSIS_MIN = """
        {
          "meta": {
            "analyzer_version": "4.0.0",
            "platform": "Linux",
            "detailed_status": "OK",
            "status_code": 0,
            "timestamp": 0,
            "analysis_time": 6.9,
            "input_process": "lib"
          },
          "track": {
            "num_samples": 100,
            "duration": 10.0,
            "sample_md5": "x",
            "offset_seconds": 0,
            "window_seconds": 0,
            "analysis_sample_rate": 44100,
            "analysis_channels": 2,
            "end_of_fade_in": 0.0,
            "start_of_fade_out": 9.5,
            "loudness": -5.0,
            "tempo": 120.0,
            "tempo_confidence": 0.9,
            "time_signature": 4,
            "time_signature_confidence": 0.8,
            "key": 5,
            "key_confidence": 0.5,
            "mode": 1,
            "mode_confidence": 0.7,
            "codestring": "x",
            "code_version": 3.15,
            "echoprintstring": "x",
            "echoprint_version": 4.15,
            "synchstring": "x",
            "synch_version": 1.0,
            "rhythmstring": "x",
            "rhythm_version": 1.0
          },
          "bars": [{"start":0.0,"duration":1.0,"confidence":1.0}],
          "beats": [{"start":0.0,"duration":0.5,"confidence":1.0}],
          "sections": [{
            "start": 0.0,
            "duration": 10.0,
            "confidence": 1.0,
            "loudness": -5.0,
            "tempo": 120.0,
            "tempo_confidence": 0.8,
            "key": 5,
            "key_confidence": 0.7,
            "mode": 1,
            "mode_confidence": 0.9,
            "time_signature": 4,
            "time_signature_confidence": 0.9
          }],
          "segments": [{
            "start": 0.0,
            "duration": 0.5,
            "confidence": 1.0,
            "loudness": -6.0,
            "loudness_start": -10.0,
            "loudness_max": -5.0,
            "loudness_max_time": 0.2,
            "loudness_end": -8.0,
            "pitches": [0.1],
            "timbre": [0.2]
          }],
          "tatums": [{"start":0.0,"duration":0.25,"confidence":1.0}]
        }
    """
    const val RECOMMENDATIONS_MIN = """
        {
          "seeds": [
            {
              "after_filtering_size": 10,
              "after_relinking_size": 10,
              "href": "https://api.spotify.com/v1/genres/pop",
              "id": "pop",
              "initial_pool_size": 100,
              "type": "GENRE"
            }
          ],
          "tracks": [
            {
              "id": "t1",
              "name": "Track 1",
              "type": "track",
              "uri": "spotify:track:t1",
              "artists": [],
              "available_markets": [],
              "disc_number": 1,
              "duration_ms": 200000,
              "explicit": false,
              "external_ids": {},
              "external_urls": {},
              "href": "https://api.spotify.com/v1/tracks/t1",
              "is_playable": true,
              "restrictions": null,
              "popularity": 10,
              "preview_url": null,
              "track_number": 1,
              "is_local": false,
              "album": {
                "album_type": "album",
                "total_tracks": 1,
                "available_markets": [],
                "external_urls": {},
                "href": "https://api.spotify.com/v1/albums/a1",
                "id": "a1",
                "images": [],
                "name": "Album 1",
                "release_date": "2024-01-01",
                "release_date_precision": "day",
                "type": "album",
                "uri": "spotify:album:a1",
                "artists": []
              }
            }
          ]
        }
    """

    const val USERS_SAVED_ALBUMS_MIN = """
        {
          "href": "https://api.spotify.com/v1/me/albums",
          "limit": 20,
          "offset": 0,
          "total": 1,
          "items": [
            {
              "added_at": "2024-01-01T00:00:00Z",
              "album": {
                "album_type": "album",
                "total_tracks": 1,
                "available_markets": [],
                "external_urls": { "spotify": "https://open.spotify.com/album/1" },
                "href": "https://api.spotify.com/v1/albums/1",
                "id": "1",
                "images": [],
                "name": "Album Name",
                "release_date": "2024-01-01",
                "release_date_precision": "day",
                "type": "album",
                "uri": "spotify:album:1",
                "artists": [],
                "tracks": {
                  "href": "https://api.spotify.com/v1/albums/1/tracks",
                  "limit": 20,
                  "offset": 0,
                  "total": 0,
                  "items": []
                },
                "copyrights": [],
                "external_ids": {},
                "label": "Label",
                "popularity": 0
              }
            }
          ]
        }
    """
}
