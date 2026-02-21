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
}
