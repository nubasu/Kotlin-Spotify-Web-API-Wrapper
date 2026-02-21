package com.nubasu.kotlin_spotify_web_api_wrapper.api.fixtures

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
