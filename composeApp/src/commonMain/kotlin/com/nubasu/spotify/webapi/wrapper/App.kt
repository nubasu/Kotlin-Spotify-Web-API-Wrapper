package com.nubasu.spotify.webapi.wrapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nubasu.spotify.webapi.wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.spotify.webapi.wrapper.api.player.PlayerApis
import com.nubasu.spotify.webapi.wrapper.api.playlists.PlaylistsApis
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import com.nubasu.spotify.webapi.wrapper.response.player.Device
import com.nubasu.spotify.webapi.wrapper.response.playlists.SimplifiedPlaylistObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    callbackUriFromIntent: String? = null,
    desktopCallbackCoordinator: DesktopCallbackCoordinator? = null,
) {
    MaterialTheme {
        SpotifyPlayerScreen(
            callbackUriFromIntent = callbackUriFromIntent,
            desktopCallbackCoordinator = desktopCallbackCoordinator,
        )
    }
}

@Composable
private fun SpotifyPlayerScreen(
    callbackUriFromIntent: String?,
    desktopCallbackCoordinator: DesktopCallbackCoordinator?,
) {
    val coroutineScope = rememberCoroutineScope()
    val playlistsApis = remember { PlaylistsApis() }
    val playerApis = remember { PlayerApis() }

    var clientId by remember { mutableStateOf("") }
    var redirectUri by remember { mutableStateOf("spotifyauth://callback") }
    var scopeText by remember {
        mutableStateOf(
            "playlist-read-private playlist-read-collaborative user-read-playback-state user-modify-playback-state"
        )
    }
    var callbackUri by remember { mutableStateOf("") }
    var authManager by remember { mutableStateOf<SpotifyAuthManager?>(null) }
    var lastHandledCallbackUri by remember { mutableStateOf<String?>(null) }
    var statusMessage by remember { mutableStateOf("Not authenticated") }
    var isLoading by remember { mutableStateOf(false) }
    var devices by remember { mutableStateOf<List<Device>>(emptyList()) }
    var selectedDeviceId by remember { mutableStateOf<String?>(null) }
    var playlists by remember { mutableStateOf<List<SimplifiedPlaylistObject>>(emptyList()) }

    LaunchedEffect(callbackUriFromIntent) {
        val value = callbackUriFromIntent?.trim().orEmpty()
        if (value.isNotEmpty()) {
            callbackUri = value
            statusMessage = "Callback URI received from app intent"
        }
    }

    LaunchedEffect(desktopCallbackCoordinator) {
        while (true) {
            val value = desktopCallbackCoordinator?.consumeCallbackUri()?.trim().orEmpty()
            if (value.isNotEmpty()) {
                callbackUri = value
                statusMessage = "Callback URI received from desktop listener"
            }
            delay(300L)
        }
    }

    LaunchedEffect(callbackUri, authManager) {
        val manager = authManager ?: return@LaunchedEffect
        val value = callbackUri.trim()
        if (value.isEmpty() || value == lastHandledCallbackUri) return@LaunchedEffect
        if (!value.contains("code=") || !value.contains("state=")) return@LaunchedEffect

        isLoading = true
        runCatching {
            manager.completePkceAuthorizationFromRedirectUri(value)
        }.onSuccess {
            lastHandledCallbackUri = value
            statusMessage = "Authenticated from app callback URI"
        }.onFailure {
            statusMessage = "Auto auth completion failed: ${it.message}"
        }
        isLoading = false
    }

    LazyColumn(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Spotify Simple Player", style = MaterialTheme.typography.headlineSmall)
        }

        item {
            OutlinedTextField(
                value = clientId,
                onValueChange = { clientId = it },
                label = { Text("Client ID") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            OutlinedTextField(
                value = redirectUri,
                onValueChange = { redirectUri = it },
                label = { Text("Redirect URI") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            OutlinedTextField(
                value = scopeText,
                onValueChange = { scopeText = it },
                label = { Text("Scopes (space/comma separated)") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Button(
                onClick = {
                    val normalizedClientId = clientId.trim()
                    var normalizedRedirectUri = redirectUri.trim()
                    if (normalizedClientId.isBlank() || normalizedRedirectUri.isBlank()) {
                        statusMessage = "Client ID and Redirect URI are required"
                        return@Button
                    }

                    val desktopRedirectUri = desktopCallbackCoordinator?.beginSession()
                    if (!desktopRedirectUri.isNullOrBlank()) {
                        normalizedRedirectUri = desktopRedirectUri
                        redirectUri = desktopRedirectUri
                        statusMessage = "Desktop callback listener started: $desktopRedirectUri"
                    }

                    val manager = SpotifyAuthManager(
                        clientId = normalizedClientId,
                        redirectUri = normalizedRedirectUri,
                    )
                    val request = manager.startPkceAuthorizationAndLaunch(
                        scope = parseScopes(scopeText),
                    )
                    authManager = manager
                    statusMessage = "Auth started. state=${request.state.take(8)}..."
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Start PKCE Auth")
            }
        }

        item {
            OutlinedTextField(
                value = callbackUri,
                onValueChange = { callbackUri = it },
                label = { Text("Callback URI") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Button(
                onClick = {
                    val manager = authManager
                    if (manager == null) {
                        statusMessage = "Start auth first"
                        return@Button
                    }

                    val redirected = callbackUri.trim()
                    if (redirected.isBlank()) {
                        statusMessage = "Callback URI is required"
                        return@Button
                    }

                    coroutineScope.launch {
                        isLoading = true
                        runCatching {
                            manager.completePkceAuthorizationFromRedirectUri(redirected)
                        }.onSuccess {
                            statusMessage = "Authenticated. Access token is ready"
                        }.onFailure {
                            statusMessage = "Auth failed: ${it.message}"
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Complete Auth from Callback URI")
            }
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        val response = playlistsApis.getCurrentUsersPlaylists(
                            pagingOptions = PagingOptions(limit = 30),
                        )
                        when (val data = response.data) {
                            is SpotifyResponseData.Success -> {
                                playlists = data.value.items
                                statusMessage = "Playlists loaded: ${data.value.items.size}"
                            }

                            is SpotifyResponseData.Error -> {
                                statusMessage = responseError("Load playlists", response)
                            }
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Load User Playlists")
            }
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        val response = playerApis.getAvailableDevices()
                        when (val data = response.data) {
                            is SpotifyResponseData.Success -> {
                                devices = data.value.devices
                                selectedDeviceId = devices.firstOrNull { it.id != null }?.id
                                statusMessage = "Devices loaded: ${devices.size}"
                            }

                            is SpotifyResponseData.Error -> {
                                statusMessage = responseError("Load devices", response)
                            }
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Load Playback Devices")
            }
        }

        if (devices.isNotEmpty()) {
            item {
                Text("Devices", style = MaterialTheme.typography.titleMedium)
            }

            items(devices, key = { it.id ?: it.name ?: "unknown-device" }) { device ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${device.name ?: "Unknown"} (${device.type ?: "Unknown"})")
                        Text("id=${device.id ?: "N/A"}")
                        Text("active=${device.isActive ?: false}")
                        Button(
                            onClick = { selectedDeviceId = device.id },
                            enabled = device.id != null,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            val selected = selectedDeviceId == device.id && device.id != null
                            Text(if (selected) "Selected" else "Use this device")
                        }
                    }
                }
            }
        }

        item {
            Text("Playlists", style = MaterialTheme.typography.titleMedium)
        }

        items(playlists, key = { it.id }) { playlist ->
            PlaylistRow(
                playlist = playlist,
                onPlay = {
                    coroutineScope.launch {
                        isLoading = true
                        val response = playerApis.startResumePlayback(
                            deviceId = selectedDeviceId,
                            contextUri = playlist.uri,
                        )
                        statusMessage = when (response.data) {
                            is SpotifyResponseData.Success -> "Playing: ${playlist.name}"
                            is SpotifyResponseData.Error -> responseError("Start playback", response)
                        }
                        isLoading = false
                    }
                },
            )
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        val response = playerApis.pausePlayback(deviceId = selectedDeviceId)
                        statusMessage = when (response.data) {
                            is SpotifyResponseData.Success -> "Paused"
                            is SpotifyResponseData.Error -> responseError("Pause playback", response)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Pause")
            }
        }

        item {
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        item {
            Text(statusMessage)
        }
    }
}

@Composable
private fun PlaylistRow(
    playlist: SimplifiedPlaylistObject,
    onPlay: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(playlist.name, style = MaterialTheme.typography.titleMedium)
            Text("owner=${playlist.owner.displayName ?: playlist.owner.id}")
            Text("tracks=${playlist.items?.total ?: playlist.tracks.total}")
            Button(
                onClick = onPlay,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Play this playlist")
            }
        }
    }
}

private fun parseScopes(value: String): List<String> {
    return value.split(",", " ", "\n", "\t")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
}

private fun responseError(
    action: String,
    response: SpotifyApiResponse<*>,
): String {
    val data = response.data
    return if (data is SpotifyResponseData.Error) {
        "$action failed (status=${response.statusCode}): ${data.value.error.message}"
    } else {
        "$action failed (status=${response.statusCode})"
    }
}
